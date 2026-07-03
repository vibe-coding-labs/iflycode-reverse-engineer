#!/usr/bin/env python3
"""
iFlyCode H() String Deobfuscator
Decodes XOR-obfuscated strings from iFlyCode 3.4.2-222 .class files.

H() algorithm:
  1. new LinkageError().getStackTrace() → get caller class name + method name
  2. key = callerClassName + callerMethodName
  3. result[i] = obfuscated[i] ^ key[i % len(key)]

Usage:
  python3 h_deobfuscator.py [class_dir] [--output results.json] [--verbose]
"""

import struct
import os
import json
import sys
import re
from collections import defaultdict

# JVM bytecode opcodes
OP_LDC = 0x12
OP_LDC_W = 0x13
OP_LDC2_W = 0x14
OP_INVOKESTATIC = 0xB8
OP_INVOKEVIRTUAL = 0xB6
OP_INVOKESPECIAL = 0xB7
OP_INVOKEINTERFACE = 0xB9
OP_TABLESWITCH = 0xAA
OP_LOOKUPSWITCH = 0xAB
OP_WIDE = 0xC4

# Complete JVM opcode length table (opcode → total byte length including opcode itself)
# -1 = variable length (handled specially)
OPCODE_LENGTHS = {
    0x00: 1,  # nop
    0x01: 1,  # aconst_null
    0x02: 1,  # iconst_m1
    0x03: 1, 0x04: 1, 0x05: 1, 0x06: 1, 0x07: 1, 0x08: 1,  # iconst_0..5
    0x09: 1, 0x0A: 1,  # lconst_0, lconst_1
    0x0B: 1, 0x0C: 1, 0x0D: 1, 0x0E: 1,  # fconst_0..2
    0x0F: 1, 0x10: 1,  # dconst_0, dconst_1
    0x11: 2,  # bipush
    0x12: 2,  # ldc
    0x13: 3,  # ldc_w
    0x14: 3,  # ldc2_w
    0x15: 2,  # iload
    0x16: 2,  # lload
    0x17: 2,  # fload
    0x18: 2,  # dload
    0x19: 2,  # aload
    0x1A: 1, 0x1B: 1, 0x1C: 1, 0x1D: 1,  # iload_0..3
    0x1E: 1, 0x1F: 1, 0x20: 1, 0x21: 1,  # lload_0..3
    0x22: 1, 0x23: 1, 0x24: 1, 0x25: 1,  # fload_0..3
    0x26: 1, 0x27: 1, 0x28: 1, 0x29: 1,  # dload_0..3
    0x2A: 1, 0x2B: 1, 0x2C: 1, 0x2D: 1,  # aload_0..3
    0x2E: 1,  # iaload
    0x2F: 1,  # laload
    0x30: 1,  # faload
    0x31: 1,  # daload
    0x32: 1,  # aaload
    0x33: 1,  # baload
    0x34: 1,  # caload
    0x35: 1,  # saload
    0x36: 2,  # istore
    0x37: 2,  # lstore
    0x38: 2,  # fstore
    0x39: 2,  # dstore
    0x3A: 2,  # astore
    0x3B: 1, 0x3C: 1, 0x3D: 1, 0x3E: 1,  # istore_0..3
    0x3F: 1, 0x40: 1, 0x41: 1, 0x42: 1,  # lstore_0..3
    0x43: 1, 0x44: 1, 0x45: 1, 0x46: 1,  # fstore_0..3
    0x47: 1, 0x48: 1, 0x49: 1, 0x4A: 1,  # dstore_0..3
    0x4B: 1, 0x4C: 1, 0x4D: 1, 0x4E: 1,  # astore_0..3
    0x4F: 1,  # iastore
    0x50: 1,  # lastore
    0x51: 1,  # fastore
    0x52: 1,  # dastore
    0x53: 1,  # aastore
    0x54: 1,  # bastore
    0x55: 1,  # castore
    0x56: 1,  # sastore
    0x57: 1,  # pop
    0x58: 1,  # pop2
    0x59: 1,  # dup
    0x5A: 1,  # dup_x1
    0x5B: 1,  # dup_x2
    0x5C: 1,  # dup2
    0x5D: 1,  # dup2_x1
    0x5E: 1,  # dup2_x2
    0x5F: 1,  # swap
    0x60: 1,  # iadd
    0x61: 1,  # ladd
    0x62: 1,  # fadd
    0x63: 1,  # dadd
    0x64: 1,  # isub
    0x65: 1,  # lsub
    0x66: 1,  # fsub
    0x67: 1,  # dsub
    0x68: 1,  # imul
    0x69: 1,  # lmul
    0x6A: 1,  # fmul
    0x6B: 1,  # dmul
    0x6C: 1,  # idiv
    0x6D: 1,  # ldiv
    0x6E: 1,  # fdiv
    0x6F: 1,  # ddiv
    0x70: 1,  # irem
    0x71: 1,  # lrem
    0x72: 1,  # frem
    0x73: 1,  # drem
    0x74: 1,  # ineg
    0x75: 1,  # lneg
    0x76: 1,  # fneg
    0x77: 1,  # dneg
    0x78: 1,  # ishl
    0x79: 1,  # lshl
    0x7A: 1,  # ishr
    0x7B: 1,  # lshr
    0x7C: 1,  # iushr
    0x7D: 1,  # lushr
    0x7E: 1,  # iand
    0x7F: 1,  # land
    0x80: 1,  # ior
    0x81: 1,  # lor
    0x82: 1,  # ixor
    0x83: 1,  # lxor
    0x84: 3,  # iinc
    0x85: 1,  # i2l
    0x86: 1,  # i2f
    0x87: 1,  # i2d
    0x88: 1,  # l2i
    0x89: 1,  # l2f
    0x8A: 1,  # l2d
    0x8B: 1,  # f2i
    0x8C: 1,  # f2l
    0x8D: 1,  # f2d
    0x8E: 1,  # d2i
    0x8F: 1,  # d2l
    0x90: 1,  # d2f
    0x91: 1,  # i2b
    0x92: 1,  # i2c
    0x93: 1,  # i2s
    0x94: 1,  # lcmp
    0x95: 1,  # fcmpl
    0x96: 1,  # fcmpg
    0x97: 1,  # dcmpl
    0x98: 1,  # dcmpg
    0x99: 3,  # ifeq
    0x9A: 3,  # ifne
    0x9B: 3,  # iflt
    0x9C: 3,  # ifge
    0x9D: 3,  # ifgt
    0x9E: 3,  # ifle
    0x9F: 3,  # if_icmpeq
    0xA0: 3,  # if_icmpne
    0xA1: 3,  # if_icmplt
    0xA2: 3,  # if_icmpge
    0xA3: 3,  # if_icmpgt
    0xA4: 3,  # if_icmple
    0xA5: 3,  # if_acmpeq
    0xA6: 3,  # if_acmpne
    0xA7: 3,  # goto
    0xA8: 3,  # jsr
    0xA9: 2,  # ret
    0xAA: -1,  # tableswitch (variable)
    0xAB: -1,  # lookupswitch (variable)
    0xAC: 1,  # ireturn
    0xAD: 1,  # lreturn
    0xAE: 1,  # freturn
    0xAF: 1,  # dreturn
    0xB0: 1,  # areturn
    0xB1: 1,  # return
    0xB2: 2,  # getstatic
    0xB3: 2,  # putstatic
    0xB4: 2,  # getfield
    0xB5: 2,  # putfield
    0xB6: 3,  # invokevirtual
    0xB7: 3,  # invokespecial
    0xB8: 3,  # invokestatic
    0xB9: 5,  # invokeinterface
    0xBA: 1,  # invokedynamic (actually 5 bytes in Java 7+)
    0xBB: 3,  # new
    0xBC: 2,  # newarray
    0xBD: 3,  # anewarray
    0xBE: 1,  # arraylength
    0xBF: 1,  # athrow
    0xC0: 3,  # checkcast
    0xC1: 3,  # instanceof
    0xC2: 1,  # monitorenter
    0xC3: 1,  # monitorexit
    0xC4: -1,  # wide (variable)
    0xC5: 4,  # multianewarray
    0xC6: 3,  # ifnull
    0xC7: 3,  # ifnonnull
    0xC8: 5,  # goto_w
    0xC9: 5,  # jsr_w
}


def parse_constant_pool(data):
    """Parse Java .class file constant pool."""
    pos = 8  # skip magic + version
    cp_count = struct.unpack('>H', data[pos:pos+2])[0]
    pos += 2

    constants = [None]  # 1-indexed
    i = 1
    while i < cp_count:
        if pos >= len(data):
            break
        tag = data[pos]
        pos += 1

        if tag == 1:  # CONSTANT_Utf8
            length = struct.unpack('>H', data[pos:pos+2])[0]
            pos += 2
            value = data[pos:pos+length].decode('utf-8', errors='replace')
            pos += length
            constants.append(('Utf8', value))
        elif tag == 7:  # CONSTANT_Class
            ni = struct.unpack('>H', data[pos:pos+2])[0]
            pos += 2
            constants.append(('Class', ni))
        elif tag == 8:  # CONSTANT_String
            si = struct.unpack('>H', data[pos:pos+2])[0]
            pos += 2
            constants.append(('String', si))
        elif tag == 10:  # CONSTANT_Methodref
            ci = struct.unpack('>H', data[pos:pos+2])[0]
            ni = struct.unpack('>H', data[pos+2:pos+4])[0]
            pos += 4
            constants.append(('Methodref', (ci, ni)))
        elif tag == 12:  # CONSTANT_NameAndType
            ni = struct.unpack('>H', data[pos:pos+2])[0]
            di = struct.unpack('>H', data[pos+2:pos+4])[0]
            pos += 4
            constants.append(('NameAndType', (ni, di)))
        elif tag in (3, 4):  # Integer, Float
            pos += 4
            constants.append(('Num', None))
        elif tag in (5, 6):  # Long, Double
            pos += 8
            constants.append(('Wide', None))
            constants.append(None)
            i += 1
        elif tag == 9:  # Fieldref
            pos += 4
            constants.append(('Fieldref', None))
        elif tag == 11:  # InterfaceMethodref
            pos += 4
            constants.append(('IFMethodref', None))
        elif tag == 15:  # MethodHandle
            pos += 3
            constants.append(('MH', None))
        elif tag == 16:  # MethodType
            pos += 2
            constants.append(('MT', None))
        elif tag in (17, 18):  # Dynamic, InvokeDynamic
            pos += 4
            constants.append(('Dyn', None))
        elif tag in (19, 20):  # Module, Package
            pos += 2
            constants.append(('Mod', None))
        else:
            break
        i += 1

    return constants, pos, cp_count


def resolve_utf8(constants, idx):
    """Resolve a constant pool index to its Utf8 string value."""
    if idx < 0 or idx >= len(constants):
        return None
    c = constants[idx]
    if c is None:
        return None
    if c[0] == 'Utf8':
        return c[1]
    return None


def resolve_class_name(constants, class_idx):
    """Resolve a Class constant to its class name string."""
    if class_idx >= len(constants):
        return None
    c = constants[class_idx]
    if c and c[0] == 'Class':
        return resolve_utf8(constants, c[1])
    return None


def resolve_methodref(constants, methodref_idx):
    """Resolve a Methodref to (class_name, method_name, method_desc)."""
    if methodref_idx >= len(constants):
        return None
    c = constants[methodref_idx]
    if not c or c[0] != 'Methodref':
        return None
    class_idx, nat_idx = c[1]

    class_name = resolve_class_name(constants, class_idx)

    nat = constants[nat_idx]
    if not nat or nat[0] != 'NameAndType':
        return None
    name_idx, desc_idx = nat[1]
    method_name = resolve_utf8(constants, name_idx)
    method_desc = resolve_utf8(constants, desc_idx)

    return (class_name, method_name, method_desc)


def parse_methods(data, constants, pos_after_cp, cp_count):
    """Parse method_info structures and extract bytecode."""
    pos = pos_after_cp

    # access_flags, this_class, super_class
    access_flags = struct.unpack('>H', data[pos:pos+2])[0]
    pos += 2
    this_class_idx = struct.unpack('>H', data[pos:pos+2])[0]
    pos += 2
    super_class_idx = struct.unpack('>H', data[pos:pos+2])[0]
    pos += 2

    # interfaces
    interfaces_count = struct.unpack('>H', data[pos:pos+2])[0]
    pos += 2 + interfaces_count * 2

    # fields
    fields_count = struct.unpack('>H', data[pos:pos+2])[0]
    pos += 2
    for _ in range(fields_count):
        pos += 6  # access_flags + name_index + descriptor_index
        attr_count = struct.unpack('>H', data[pos:pos+2])[0]
        pos += 2
        for _ in range(attr_count):
            pos += 2  # attr_name_index
            attr_len = struct.unpack('>I', data[pos:pos+4])[0]
            pos += 4 + attr_len

    # methods
    methods_count = struct.unpack('>H', data[pos:pos+2])[0]
    pos += 2

    methods = []
    for _ in range(methods_count):
        m_access = struct.unpack('>H', data[pos:pos+2])[0]
        m_name_idx = struct.unpack('>H', data[pos+2:pos+4])[0]
        m_desc_idx = struct.unpack('>H', data[pos+4:pos+6])[0]
        pos += 6

        m_name = resolve_utf8(constants, m_name_idx)
        m_desc = resolve_utf8(constants, m_desc_idx)

        bytecode = None
        attr_count = struct.unpack('>H', data[pos:pos+2])[0]
        pos += 2
        for _ in range(attr_count):
            attr_name_idx = struct.unpack('>H', data[pos:pos+2])[0]
            attr_len = struct.unpack('>I', data[pos+2:pos+6])[0]
            attr_name = resolve_utf8(constants, attr_name_idx)

            if attr_name == 'Code':
                # max_stack(2) + max_locals(2) + code_length(4) + code + ...
                code_start = pos + 6
                code_length = struct.unpack('>I', data[pos+6:pos+10])[0]
                bytecode = data[code_start:code_start+code_length]

            pos += 6 + attr_len

        methods.append({
            'name': m_name,
            'desc': m_desc,
            'access': m_access,
            'bytecode': bytecode
        })

    return methods, this_class_idx


def find_h_calls_in_method(method, constants):
    """Find all H() calls in a method's bytecode and extract their String arguments."""
    bytecode = method.get('bytecode')
    if not bytecode:
        return []

    results = []
    pos = 0
    last_ldc_string = None  # Track the last ldc loaded String

    while pos < len(bytecode):
        opcode = bytecode[pos]

        if opcode == OP_LDC:
            idx = bytecode[pos + 1]
            if idx < len(constants) and constants[idx]:
                c = constants[idx]
                if c[0] == 'String':
                    last_ldc_string = resolve_utf8(constants, c[1])
                else:
                    last_ldc_string = None
            pos += 2

        elif opcode == OP_LDC_W:
            idx = struct.unpack('>H', bytecode[pos+1:pos+3])[0]
            if idx < len(constants) and constants[idx]:
                c = constants[idx]
                if c[0] == 'String':
                    last_ldc_string = resolve_utf8(constants, c[1])
                else:
                    last_ldc_string = None
            pos += 3

        elif opcode == OP_INVOKESTATIC:
            idx = struct.unpack('>H', bytecode[pos+1:pos+3])[0]
            ref = resolve_methodref(constants, idx)
            if ref:
                class_name, method_name, method_desc = ref
                if method_name == 'H' and '(Ljava/lang/Object;)Ljava/lang/String;' in (method_desc or ''):
                    results.append({
                        'obfuscated': last_ldc_string,
                        'target_class': class_name,
                        'caller_method': method['name'],
                    })
            last_ldc_string = None
            pos += 3

        elif opcode == OP_INVOKEVIRTUAL or opcode == OP_INVOKESPECIAL:
            idx = struct.unpack('>H', bytecode[pos+1:pos+3])[0]
            ref = resolve_methodref(constants, idx)
            if ref:
                class_name, method_name, method_desc = ref
                if method_name == 'H' and '(Ljava/lang/Object;)Ljava/lang/String;' in (method_desc or ''):
                    results.append({
                        'obfuscated': last_ldc_string,
                        'target_class': class_name,
                        'caller_method': method['name'],
                    })
            last_ldc_string = None
            pos += 3

        elif opcode == OP_INVOKEINTERFACE:
            pos += 5
            last_ldc_string = None

        elif opcode == 0xBA:  # invokedynamic
            pos += 5
            last_ldc_string = None

        elif opcode == OP_TABLESWITCH:
            pad = (4 - ((pos + 1) % 4)) % 4
            base = pos + 1 + pad
            if base + 12 > len(bytecode):
                break
            low = struct.unpack('>i', bytecode[base+4:base+8])[0]
            high = struct.unpack('>i', bytecode[base+8:base+12])[0]
            pos = base + 12 + (high - low + 1) * 4
            last_ldc_string = None

        elif opcode == OP_LOOKUPSWITCH:
            pad = (4 - ((pos + 1) % 4)) % 4
            base = pos + 1 + pad
            if base + 8 > len(bytecode):
                break
            npairs = struct.unpack('>i', bytecode[base+4:base+8])[0]
            pos = base + 8 + npairs * 8
            last_ldc_string = None

        elif opcode == OP_WIDE:
            if pos + 1 < len(bytecode):
                wide_op = bytecode[pos + 1]
                if wide_op == 0x84:  # iinc
                    pos += 6
                else:
                    pos += 4
            else:
                pos += 1
            last_ldc_string = None

        else:
            # Use opcode length table
            length = OPCODE_LENGTHS.get(opcode, 1)
            if length < 0:
                # Unknown variable-length opcode, skip 1 byte and hope
                pos += 1
                last_ldc_string = None
            else:
                pos += length
                # Only preserve ldc_string for opcodes that don't consume the stack
                # For simplicity, clear it on any non-ldc, non-invoke opcode
                # Actually, we should keep it for stack-neutral ops
                # Let's be more precise: clear on opcodes that modify the operand stack
                stack_modifying = {
                    0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
                    0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10,
                    0x11, 0x15, 0x16, 0x17, 0x18, 0x19,
                    0x36, 0x37, 0x38, 0x39, 0x3A,
                    0x57, 0x58, 0x59, 0x5A, 0x5B, 0x5C, 0x5D, 0x5E, 0x5F,
                    0xB2, 0xB3, 0xB4, 0xB5,  # field ops
                    0xBB, 0xBC, 0xBD,  # new, newarray, anewarray
                    0xC0, 0xC1,  # checkcast, instanceof
                    0xC5,  # multianewarray
                }
                if opcode in stack_modifying:
                    last_ldc_string = None

    return results


def xor_decode(obfuscated, key):
    """XOR decode an obfuscated string with the given key."""
    if not obfuscated or not key:
        return None
    result = []
    for i, c in enumerate(obfuscated):
        result.append(chr(ord(c) ^ ord(key[i % len(key)])))
    return ''.join(result)


def get_simple_class_name(full_name):
    """Extract simple class name from full internal name."""
    if '/' in full_name:
        return full_name.rsplit('/', 1)[1]
    return full_name


def analyze_class_file(filepath, verbose=False):
    """Analyze a single .class file for H() calls."""
    try:
        with open(filepath, 'rb') as f:
            data = f.read()
        if len(data) < 10:
            return None
        if struct.unpack('>I', data[:4])[0] != 0xCAFEBABE:
            return None
    except Exception:
        return None

    constants, pos_after_cp, cp_count = parse_constant_pool(data)
    methods, this_class_idx = parse_methods(data, constants, pos_after_cp, cp_count)

    class_name = resolve_class_name(constants, this_class_idx)
    simple_name = get_simple_class_name(class_name) if class_name else None

    all_h_calls = []
    for method in methods:
        h_calls = find_h_calls_in_method(method, constants)
        for call in h_calls:
            call['caller_class'] = class_name
            call['caller_class_simple'] = simple_name
            all_h_calls.append(call)

    return {
        'class_name': class_name,
        'simple_name': simple_name,
        'methods': len(methods),
        'h_calls': all_h_calls
    }


def main():
    class_dir = sys.argv[1] if len(sys.argv) > 1 else \
        os.path.expanduser('~/github/vibe-coding-labs/iflycode-RE/extracted/jar-contents/com/aicode')

    output_file = None
    verbose = False
    for arg in sys.argv[2:]:
        if arg == '--verbose':
            verbose = True
        elif arg == '--output':
            output_file = sys.argv[sys.argv.index('--output') + 1]

    # Scan all .class files
    all_results = []
    total_h_calls = 0
    decoded_count = 0
    failed_count = 0

    # H() definition classes (where H is defined, not called)
    h_definition_classes = {
        'com/aicode/util/AICodeStringUtil',
        'com/aicode/diff/GenericUtils',
        'com/aicode/util/NewFileUtils',
        'com/aicode/util/PropertyUtils',
        'com/aicode/ui/FontKt',
        'com/aicode/util/HandleCacheUtil',
        'com/aicode/util/IndentLineUtil',
    }

    class_files = []
    for root, dirs, files in os.walk(class_dir):
        for fn in files:
            if fn.endswith('.class'):
                class_files.append(os.path.join(root, fn))

    print(f"Scanning {len(class_files)} .class files...")

    for fp in class_files:
        result = analyze_class_file(fp, verbose)
        if result and result['h_calls']:
            # Skip H() definition classes themselves
            if result['class_name'] in h_definition_classes:
                continue

            for call in result['h_calls']:
                total_h_calls += 1
                obfuscated = call.get('obfuscated')
                caller_simple = call.get('caller_class_simple', '')
                caller_method = call.get('caller_method', '')

                if obfuscated and caller_simple and caller_method:
                    key = caller_simple + caller_method
                    decoded = xor_decode(obfuscated, key)
                    call['decoded'] = decoded
                    call['xor_key'] = key
                    if decoded:
                        decoded_count += 1
                    else:
                        failed_count += 1
                else:
                    call['decoded'] = None
                    call['xor_key'] = None
                    failed_count += 1

            all_results.append(result)

    # Sort by number of H() calls
    all_results.sort(key=lambda x: -len(x['h_calls']))

    # Print summary
    print(f"\n{'='*60}")
    print(f"H() Deobfuscation Summary")
    print(f"{'='*60}")
    print(f"Classes with H() calls: {len(all_results)}")
    print(f"Total H() calls: {total_h_calls}")
    print(f"Successfully decoded: {decoded_count}")
    print(f"Failed to decode: {failed_count}")
    print(f"Success rate: {decoded_count/total_h_calls*100:.1f}%" if total_h_calls else "N/A")

    # Print decoded strings by class
    print(f"\n{'='*60}")
    print(f"Decoded Strings by Class (top 30)")
    print(f"{'='*60}")

    for result in all_results[:30]:
        cn = result['class_name']
        calls = result['h_calls']
        print(f"\n  {cn} ({len(calls)} H-calls)")
        for call in calls[:10]:  # Show first 10 per class
            decoded = call.get('decoded', '')
            obf = repr(call.get('obfuscated', '')[:30])
            if decoded:
                print(f"    {call['caller_method']}(): {repr(decoded[:80])}")
            else:
                print(f"    {call['caller_method']}(): FAILED (obf={obf})")
        if len(calls) > 10:
            print(f"    ... and {len(calls)-10} more")

    # Collect all decoded strings for analysis
    all_decoded = []
    for result in all_results:
        for call in result['h_calls']:
            if call.get('decoded'):
                all_decoded.append({
                    'class': result['class_name'],
                    'method': call['caller_method'],
                    'target': call.get('target_class', ''),
                    'obfuscated': call.get('obfuscated', ''),
                    'decoded': call['decoded'],
                    'key': call.get('xor_key', ''),
                })

    # Category analysis
    categories = defaultdict(list)
    for d in all_decoded:
        decoded = d['decoded']
        if decoded.startswith('http') or decoded.startswith('ws'):
            categories['URL'].append(decoded)
        elif decoded.startswith('/') or decoded.startswith('\\'):
            categories['Path'].append(decoded)
        elif decoded in ('EXPLAIN', 'COMMENT', 'OPTIMIZE', 'FIX', 'REFACTOR', 'GENERATE', 'DEBUG', 'TEST', 'SPLIT'):
            categories['InlineChat Category'].append(decoded)
        elif decoded.isupper() and len(decoded) > 3 and '_' in decoded:
            categories['Enum/Constant'].append(decoded)
        elif any(c in decoded for c in '={}<>'):
            categories['Code/Config'].append(decoded)
        elif any('一' <= c <= '鿿' for c in decoded):
            categories['Chinese Text'].append(decoded)
        else:
            categories['Other'].append(decoded)

    print(f"\n{'='*60}")
    print(f"Decoded String Categories")
    print(f"{'='*60}")
    for cat, items in sorted(categories.items(), key=lambda x: -len(x[1])):
        print(f"\n  {cat} ({len(items)} items)")
        for item in sorted(set(items))[:20]:
            print(f"    {repr(item[:80])}")
        if len(set(items)) > 20:
            print(f"    ... and {len(set(items))-20} more unique")

    # Save to JSON
    if output_file:
        with open(output_file, 'w', encoding='utf-8') as f:
            json.dump({
                'summary': {
                    'total_h_calls': total_h_calls,
                    'decoded_count': decoded_count,
                    'failed_count': failed_count,
                    'classes_with_h_calls': len(all_results),
                },
                'decoded_strings': all_decoded,
                'categories': {k: list(set(v)) for k, v in categories.items()},
            }, f, ensure_ascii=False, indent=2)
        print(f"\nResults saved to {output_file}")


if __name__ == '__main__':
    main()
