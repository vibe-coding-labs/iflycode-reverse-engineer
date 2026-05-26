#!/usr/bin/env python3
"""
Java .class file parser for reverse engineering.
Extracts class info, fields, methods, and bytecode disassembly.
"""
import struct
import sys
import os
from collections import OrderedDict

# Constant pool tags
CP_UTF8 = 1
CP_INTEGER = 3
CP_FLOAT = 4
CP_LONG = 5
CP_DOUBLE = 6
CP_CLASS = 7
CP_STRING = 8
CP_FIELDREF = 9
CP_METHODREF = 10
CP_INTERFACE_METHODREF = 11
CP_NAME_AND_TYPE = 12
CP_METHOD_HANDLE = 15
CP_METHOD_TYPE = 16
CP_INVOKE_DYNAMIC = 18
CP_MODULE = 19
CP_PACKAGE = 20
CP_DYNAMIC = 17

# Access flags
ACC_PUBLIC = 0x0001
ACC_PRIVATE = 0x0002
ACC_PROTECTED = 0x0004
ACC_STATIC = 0x0008
ACC_FINAL = 0x0010
ACC_SUPER = 0x0020
ACC_SYNCHRONIZED = 0x0020
ACC_VOLATILE = 0x0040
ACC_BRIDGE = 0x0040
ACC_TRANSIENT = 0x0080
ACC_VARARGS = 0x0080
ACC_NATIVE = 0x0100
ACC_INTERFACE = 0x0200
ACC_ABSTRACT = 0x0400
ACC_STRICT = 0x0800
ACC_SYNTHETIC = 0x1000
ACC_ANNOTATION = 0x2000
ACC_ENUM = 0x4000

ACCESS_FLAGS = {
    ACC_PUBLIC: 'public', ACC_PRIVATE: 'private', ACC_PROTECTED: 'protected',
    ACC_STATIC: 'static', ACC_FINAL: 'final', ACC_SUPER: 'super',
    ACC_SYNCHRONIZED: 'synchronized', ACC_VOLATILE: 'volatile',
    ACC_TRANSIENT: 'transient', ACC_NATIVE: 'native', ACC_INTERFACE: 'interface',
    ACC_ABSTRACT: 'abstract', ACC_STRICT: 'strictfp', ACC_SYNTHETIC: 'synthetic',
    ACC_ANNOTATION: 'annotation', ACC_ENUM: 'enum', ACC_BRIDGE: 'bridge',
    ACC_VARARGS: 'varargs',
}

# Opcodes
OPCODES = {
    0x00: ('nop', 0), 0x01: ('aconst_null', 0), 0x02: ('iconst_m1', 0),
    0x03: ('iconst_0', 0), 0x04: ('iconst_1', 0), 0x05: ('iconst_2', 0),
    0x06: ('iconst_3', 0), 0x07: ('iconst_4', 0), 0x08: ('iconst_5', 0),
    0x09: ('lconst_0', 0), 0x0a: ('lconst_1', 0), 0x0b: ('fconst_0', 0),
    0x0c: ('fconst_1', 0), 0x0d: ('fconst_2', 0), 0x0e: ('dconst_0', 0),
    0x0f: ('dconst_1', 0), 0x10: ('bipush', 1), 0x11: ('sipush', 2),
    0x12: ('ldc', 1), 0x13: ('ldc_w', 2), 0x14: ('ldc2_w', 2),
    0x15: ('iload', 1), 0x16: ('lload', 1), 0x17: ('fload', 1),
    0x18: ('dload', 1), 0x19: ('aload', 1), 0x1a: ('iload_0', 0),
    0x1b: ('iload_1', 0), 0x1c: ('iload_2', 0), 0x1d: ('iload_3', 0),
    0x1e: ('lload_0', 0), 0x1f: ('lload_1', 0), 0x20: ('lload_2', 0),
    0x21: ('lload_3', 0), 0x22: ('fload_0', 0), 0x23: ('fload_1', 0),
    0x24: ('fload_2', 0), 0x25: ('fload_3', 0), 0x26: ('dload_0', 0),
    0x27: ('dload_1', 0), 0x28: ('dload_2', 0), 0x29: ('dload_3', 0),
    0x2a: ('aload_0', 0), 0x2b: ('aload_1', 0), 0x2c: ('aload_2', 0),
    0x2d: ('aload_3', 0), 0x2e: ('iaload', 0), 0x2f: ('laload', 0),
    0x30: ('faload', 0), 0x31: ('daload', 0), 0x32: ('aaload', 0),
    0x33: ('baload', 0), 0x34: ('caload', 0), 0x35: ('saload', 0),
    0x36: ('istore', 1), 0x37: ('lstore', 1), 0x38: ('fstore', 1),
    0x39: ('dstore', 1), 0x3a: ('astore', 1), 0x3b: ('istore_0', 0),
    0x3c: ('istore_1', 0), 0x3d: ('istore_2', 0), 0x3e: ('istore_3', 0),
    0x3f: ('lstore_0', 0), 0x40: ('lstore_1', 0), 0x41: ('lstore_2', 0),
    0x42: ('lstore_3', 0), 0x43: ('fstore_0', 0), 0x44: ('fstore_1', 0),
    0x45: ('fstore_2', 0), 0x46: ('fstore_3', 0), 0x47: ('dstore_0', 0),
    0x48: ('dstore_1', 0), 0x49: ('dstore_2', 0), 0x4a: ('dstore_3', 0),
    0x4b: ('astore_0', 0), 0x4c: ('astore_1', 0), 0x4d: ('astore_2', 0),
    0x4e: ('astore_3', 0), 0x4f: ('iastore', 0), 0x50: ('lastore', 0),
    0x51: ('fastore', 0), 0x52: ('dastore', 0), 0x53: ('aastore', 0),
    0x54: ('bastore', 0), 0x55: ('castore', 0), 0x56: ('sastore', 0),
    0x57: ('pop', 0), 0x58: ('pop2', 0), 0x59: ('dup', 0),
    0x5a: ('dup_x1', 0), 0x5b: ('dup_x2', 0), 0x5c: ('dup2', 0),
    0x5d: ('dup2_x1', 0), 0x5e: ('dup2_x2', 0), 0x5f: ('swap', 0),
    0x60: ('iadd', 0), 0x61: ('ladd', 0), 0x62: ('fadd', 0),
    0x63: ('dadd', 0), 0x64: ('isub', 0), 0x65: ('lsub', 0),
    0x66: ('fsub', 0), 0x67: ('dsub', 0), 0x68: ('imul', 0),
    0x69: ('lmul', 0), 0x6a: ('fmul', 0), 0x6b: ('dmul', 0),
    0x6c: ('idiv', 0), 0x6d: ('ldiv', 0), 0x6e: ('fdiv', 0),
    0x6f: ('ddiv', 0), 0x70: ('irem', 0), 0x71: ('lrem', 0),
    0x72: ('frem', 0), 0x73: ('drem', 0), 0x74: ('ineg', 0),
    0x75: ('lneg', 0), 0x76: ('fneg', 0), 0x77: ('dneg', 0),
    0x78: ('ishl', 0), 0x79: ('lshl', 0), 0x7a: ('ishr', 0),
    0x7b: ('lshr', 0), 0x7c: ('iushr', 0), 0x7d: ('lushr', 0),
    0x7e: ('iand', 0), 0x7f: ('land', 0), 0x80: ('ior', 0),
    0x81: ('lor', 0), 0x82: ('ixor', 0), 0x83: ('lxor', 0),
    0x84: ('iinc', 2), 0x85: ('i2l', 0), 0x86: ('i2f', 0),
    0x87: ('i2d', 0), 0x88: ('l2i', 0), 0x89: ('l2f', 0),
    0x8a: ('l2d', 0), 0x8b: ('f2i', 0), 0x8c: ('f2l', 0),
    0x8d: ('f2d', 0), 0x8e: ('d2i', 0), 0x8f: ('d2l', 0),
    0x90: ('d2f', 0), 0x91: ('i2b', 0), 0x92: ('i2c', 0),
    0x93: ('i2s', 0), 0x94: ('lcmp', 0), 0x95: ('fcmpl', 0),
    0x96: ('fcmpg', 0), 0x97: ('dcmpl', 0), 0x98: ('dcmpg', 0),
    0x99: ('ifeq', 2), 0x9a: ('ifne', 2), 0x9b: ('iflt', 2),
    0x9c: ('ifge', 2), 0x9d: ('ifgt', 2), 0x9e: ('ifle', 2),
    0x9f: ('if_icmpeq', 2), 0xa0: ('if_icmpne', 2), 0xa1: ('if_icmplt', 2),
    0xa2: ('if_icmpge', 2), 0xa3: ('if_icmpgt', 2), 0xa4: ('if_icmple', 2),
    0xa5: ('if_acmpeq', 2), 0xa6: ('if_acmpne', 2), 0xa7: ('goto', 2),
    0xa8: ('jsr', 2), 0xa9: ('ret', 1), 0xaa: ('tableswitch', -1),
    0xab: ('lookupswitch', -1), 0xac: ('ireturn', 0), 0xad: ('lreturn', 0),
    0xae: ('freturn', 0), 0xaf: ('dreturn', 0), 0xb0: ('areturn', 0),
    0xb1: ('return', 0), 0xb2: ('getstatic', 2), 0xb3: ('putstatic', 2),
    0xb4: ('getfield', 2), 0xb5: ('putfield', 2), 0xb6: ('invokevirtual', 2),
    0xb7: ('invokespecial', 2), 0xb8: ('invokestatic', 2),
    0xb9: ('invokeinterface', 4), 0xba: ('invokedynamic', 4),
    0xbb: ('new', 2), 0xbc: ('newarray', 1), 0xbd: ('anewarray', 2),
    0xbe: ('arraylength', 0), 0xbf: ('athrow', 0), 0xc0: ('checkcast', 2),
    0xc1: ('instanceof', 2), 0xc2: ('monitorent', 0), 0xc3: ('monitorexit', 0),
    0xc4: ('wide', -1), 0xc5: ('multianewarray', 3), 0xc6: ('ifnull', 2),
    0xc7: ('ifnonnull', 2), 0xc8: ('goto_w', 4), 0xc9: ('jsr_w', 4),
}


class ClassFileParser:
    def __init__(self, data):
        self.data = data
        self.pos = 0
        self.cp = {}

    def u1(self):
        v = self.data[self.pos]
        self.pos += 1
        return v

    def u2(self):
        v = struct.unpack('>H', self.data[self.pos:self.pos+2])[0]
        self.pos += 2
        return v

    def u4(self):
        v = struct.unpack('>I', self.data[self.pos:self.pos+4])[0]
        self.pos += 4
        return v

    def s4(self):
        v = struct.unpack('>i', self.data[self.pos:self.pos+4])[0]
        self.pos += 4
        return v

    def read_bytes(self, n):
        v = self.data[self.pos:self.pos+n]
        self.pos += n
        return v

    def get_utf8(self, idx):
        if idx in self.cp and self.cp[idx][0] == CP_UTF8:
            return self.cp[idx][1]
        return f"<utf8#{idx}>"

    def get_class_name(self, idx):
        if idx == 0:
            return "void"
        if idx in self.cp and self.cp[idx][0] == CP_CLASS:
            return self.get_utf8(self.cp[idx][1]).replace('/', '.')
        return f"<class#{idx}>"

    def get_name_and_type(self, idx):
        if idx in self.cp and self.cp[idx][0] == CP_NAME_AND_TYPE:
            name = self.get_utf8(self.cp[idx][1])
            desc = self.get_utf8(self.cp[idx][2])
            return name, desc
        return f"<nat#{idx}>", ""

    def get_ref(self, idx):
        if idx in self.cp:
            tag, cidx, natidx = self.cp[idx]
            cls = self.get_class_name(cidx)
            name, desc = self.get_name_and_type(natidx)
            return f"{cls}.{name}:{desc}"
        return f"<ref#{idx}>"

    def get_string(self, idx):
        if idx in self.cp and self.cp[idx][0] == CP_STRING:
            return self.get_utf8(self.cp[idx][1])
        return f"<string#{idx}>"

    def parse_cp(self, count):
        i = 1
        while i < count:
            tag = self.u1()
            if tag == CP_UTF8:
                length = self.u2()
                val = self.read_bytes(length).decode('utf-8', errors='replace')
                self.cp[i] = (tag, val)
            elif tag == CP_INTEGER:
                self.cp[i] = (tag, self.u4())
            elif tag == CP_FLOAT:
                self.cp[i] = (tag, struct.unpack('>f', self.read_bytes(4))[0])
            elif tag == CP_LONG:
                self.cp[i] = (tag, struct.unpack('>q', self.read_bytes(8))[0])
                i += 1  # long takes 2 slots
            elif tag == CP_DOUBLE:
                self.cp[i] = (tag, struct.unpack('>d', self.read_bytes(8))[0])
                i += 1  # double takes 2 slots
            elif tag == CP_CLASS:
                self.cp[i] = (tag, self.u2())
            elif tag == CP_STRING:
                self.cp[i] = (tag, self.u2())
            elif tag in (CP_FIELDREF, CP_METHODREF, CP_INTERFACE_METHODREF):
                self.cp[i] = (tag, self.u2(), self.u2())
            elif tag == CP_NAME_AND_TYPE:
                self.cp[i] = (tag, self.u2(), self.u2())
            elif tag == CP_METHOD_HANDLE:
                self.cp[i] = (tag, self.u1(), self.u2())
            elif tag == CP_METHOD_TYPE:
                self.cp[i] = (tag, self.u2())
            elif tag == CP_INVOKE_DYNAMIC:
                self.cp[i] = (tag, self.u2(), self.u2())
            elif tag == CP_DYNAMIC:
                self.cp[i] = (tag, self.u2(), self.u2())
            elif tag == CP_MODULE:
                self.cp[i] = (tag, self.u2())
            elif tag == CP_PACKAGE:
                self.cp[i] = (tag, self.u2())
            else:
                print(f"  WARNING: Unknown CP tag {tag} at index {i}", file=sys.stderr)
                break
            i += 1

    def parse_descriptor(self, desc):
        """Parse method descriptor into parameter types and return type."""
        if not desc.startswith('('):
            return desc, []
        end = desc.index(')')
        params_str = desc[1:end]
        ret_str = desc[end+1:]

        params = []
        i = 0
        while i < len(params_str):
            ch = params_str[i]
            if ch in 'BCDFIJSZ':
                type_map = {'B':'byte','C':'char','D':'double','F':'float',
                           'I':'int','J':'long','S':'short','Z':'boolean'}
                params.append(type_map[ch])
                i += 1
            elif ch == 'L':
                end_idx = params_str.index(';', i)
                params.append(params_str[i+1:end_idx].replace('/', '.'))
                i = end_idx + 1
            elif ch == '[':
                i += 1
                # Array - get base type
                arr_count = 1
                while i < len(params_str) and params_str[i] == '[':
                    arr_count += 1
                    i += 1
                if i < len(params_str):
                    if params_str[i] in 'BCDFIJSZ':
                        type_map = {'B':'byte','C':'char','D':'double','F':'float',
                                   'I':'int','J':'long','S':'short','Z':'boolean'}
                        params.append(type_map[params_str[i]] + '[]' * arr_count)
                        i += 1
                    elif params_str[i] == 'L':
                        end_idx = params_str.index(';', i)
                        params.append(params_str[i+1:end_idx].replace('/', '.') + '[]' * arr_count)
                        i = end_idx + 1
            else:
                i += 1

        # Parse return type
        ret_map = {'B':'byte','C':'char','D':'double','F':'float',
                   'I':'int','J':'long','S':'short','Z':'boolean','V':'void'}
        if ret_str in ret_map:
            ret = ret_map[ret_str]
        elif ret_str.startswith('L'):
            ret = ret_str[1:-1].replace('/', '.')
        elif ret_str.startswith('['):
            arr_count = 0
            j = 0
            while j < len(ret_str) and ret_str[j] == '[':
                arr_count += 1
                j += 1
            if ret_str[j] in ret_map:
                ret = ret_map[ret_str[j]] + '[]' * arr_count
            else:
                ret = ret_str[j+1:-1].replace('/', '.') + '[]' * arr_count
        else:
            ret = ret_str

        return ret, params

    def disassemble(self, code_bytes):
        """Disassemble bytecode into human-readable instructions."""
        lines = []
        pos = 0
        while pos < len(code_bytes):
            op = code_bytes[pos]
            if op not in OPCODES:
                lines.append(f"  {pos}: byte {op:#04x}")
                pos += 1
                continue

            name, arg_count = OPCODES[op]
            start_pos = pos
            pos += 1

            if arg_count == 0:
                lines.append(f"  {start_pos}: {name}")
            elif arg_count == -1:
                # Variable length
                if name == 'tableswitch':
                    # Padding to 4-byte boundary
                    padding = (4 - (start_pos + 1) % 4) % 4
                    pos += padding
                    default = struct.unpack('>i', code_bytes[pos:pos+4])[0]
                    pos += 4
                    low = struct.unpack('>i', code_bytes[pos:pos+4])[0]
                    pos += 4
                    high = struct.unpack('>i', code_bytes[pos:pos+4])[0]
                    pos += 4
                    offsets = []
                    for _ in range(high - low + 1):
                        off = struct.unpack('>i', code_bytes[pos:pos+4])[0]
                        pos += 4
                        offsets.append(off)
                    lines.append(f"  {start_pos}: {name} default->{start_pos+default} low={low} high={high}")
                elif name == 'lookupswitch':
                    padding = (4 - (start_pos + 1) % 4) % 4
                    pos += padding
                    default = struct.unpack('>i', code_bytes[pos:pos+4])[0]
                    pos += 4
                    npairs = struct.unpack('>i', code_bytes[pos:pos+4])[0]
                    pos += 4
                    pairs = []
                    for _ in range(npairs):
                        match = struct.unpack('>i', code_bytes[pos:pos+4])[0]
                        off = struct.unpack('>i', code_bytes[pos:pos+4])[0]
                        pos += 8
                        pairs.append((match, off))
                    lines.append(f"  {start_pos}: {name} default->{start_pos+default} npairs={npairs}")
                elif name == 'wide':
                    wide_op = code_bytes[pos]
                    pos += 1
                    if wide_op in OPCODES:
                        wide_name = OPCODES[wide_op][0]
                        if wide_op in (0x84,):  # iinc
                            idx = struct.unpack('>H', code_bytes[pos:pos+2])[0]
                            pos += 2
                            const = struct.unpack('>h', code_bytes[pos:pos+2])[0]
                            pos += 2
                            lines.append(f"  {start_pos}: wide {wide_name} {idx} {const}")
                        else:
                            idx = struct.unpack('>H', code_bytes[pos:pos+2])[0]
                            pos += 2
                            lines.append(f"  {start_pos}: wide {wide_name} {idx}")
                    else:
                        lines.append(f"  {start_pos}: wide unknown_op_{wide_op:#04x}")
            elif arg_count == 1:
                arg = code_bytes[pos]
                pos += 1
                if name == 'iinc':
                    # iinc has 2 operands: index, const
                    const = struct.unpack('>b', bytes([code_bytes[pos]]))[0]
                    pos += 1
                    lines.append(f"  {start_pos}: {name} {arg} {const}")
                elif name in ('ldc',):
                    lines.append(f"  {start_pos}: {name} #{arg} // {self.resolve_cp_arg(arg)}")
                elif name in ('iload', 'lload', 'fload', 'dload', 'aload',
                             'istore', 'lstore', 'fstore', 'dstore', 'astore', 'ret'):
                    lines.append(f"  {start_pos}: {name} {arg}")
                elif name == 'newarray':
                    type_map = {4:'boolean',5:'char',6:'float',7:'double',
                               8:'byte',9:'short',10:'int',11:'long'}
                    lines.append(f"  {start_pos}: {name} {type_map.get(arg, arg)}")
                else:
                    lines.append(f"  {start_pos}: {name} {arg}")
            elif arg_count == 2:
                arg = struct.unpack('>H', code_bytes[pos:pos+2])[0]
                pos += 2
                if name in ('getstatic', 'putstatic', 'getfield', 'putfield',
                           'invokevirtual', 'invokespecial', 'invokestatic',
                           'new', 'anewarray', 'checkcast', 'instanceof'):
                    lines.append(f"  {start_pos}: {name} #{arg} // {self.resolve_cp_arg(arg)}")
                elif name in ('ifeq', 'ifne', 'iflt', 'ifge', 'ifgt', 'ifle',
                             'if_icmpeq', 'if_icmpne', 'if_icmplt', 'if_icmpge',
                             'if_icmpgt', 'if_icmple', 'if_acmpeq', 'if_acmpne',
                             'goto', 'jsr', 'ifnull', 'ifnonnull'):
                    offset = struct.unpack('>h', code_bytes[pos-2:pos])[0]
                    lines.append(f"  {start_pos}: {name} {start_pos + offset}")
                elif name in ('ldc_w', 'ldc2_w'):
                    lines.append(f"  {start_pos}: {name} #{arg} // {self.resolve_cp_arg(arg)}")
                elif name == 'sipush':
                    val = struct.unpack('>h', code_bytes[pos-2:pos])[0]
                    lines.append(f"  {start_pos}: {name} {val}")
                else:
                    lines.append(f"  {start_pos}: {name} {arg}")
            elif arg_count == 3:
                if name == 'multianewarray':
                    arg1 = struct.unpack('>H', code_bytes[pos:pos+2])[0]
                    pos += 2
                    arg2 = code_bytes[pos]
                    pos += 1
                    lines.append(f"  {start_pos}: {name} #{arg1} {arg2} // {self.resolve_cp_arg(arg1)}")
                else:
                    pos += 3
                    lines.append(f"  {start_pos}: {name}")
            elif arg_count == 4:
                if name == 'invokeinterface':
                    arg1 = struct.unpack('>H', code_bytes[pos:pos+2])[0]
                    pos += 2
                    count = code_bytes[pos]
                    pos += 1
                    _zero = code_bytes[pos]
                    pos += 1
                    lines.append(f"  {start_pos}: {name} #{arg1} {count} // {self.resolve_cp_arg(arg1)}")
                elif name == 'invokedynamic':
                    arg1 = struct.unpack('>H', code_bytes[pos:pos+2])[0]
                    pos += 2
                    _zero = struct.unpack('>H', code_bytes[pos:pos+2])[0]
                    pos += 2
                    lines.append(f"  {start_pos}: {name} #{arg1} // {self.resolve_cp_arg(arg1)}")
                else:
                    pos += 4
                    lines.append(f"  {start_pos}: {name}")
        return lines

    def resolve_cp_arg(self, idx):
        """Resolve a constant pool index to a readable string."""
        if idx not in self.cp:
            return f"#{idx}"
        entry = self.cp[idx]
        tag = entry[0]
        if tag == CP_UTF8:
            return f'String "{entry[1]}"'
        elif tag == CP_CLASS:
            return self.get_class_name(idx)
        elif tag == CP_STRING:
            return f'String "{self.get_utf8(entry[1])}"'
        elif tag in (CP_FIELDREF, CP_METHODREF, CP_INTERFACE_METHODREF):
            return self.get_ref(idx)
        elif tag == CP_NAME_AND_TYPE:
            name, desc = self.get_name_and_type(idx)
            return f"{name}:{desc}"
        elif tag == CP_INTEGER:
            return f"int {entry[1]}"
        elif tag == CP_FLOAT:
            return f"float {entry[1]}"
        elif tag == CP_LONG:
            return f"long {entry[1]}"
        elif tag == CP_DOUBLE:
            return f"double {entry[1]}"
        elif tag == CP_METHOD_HANDLE:
            return f"MethodHandle ref_kind={entry[1]} ref=#{entry[2]}"
        elif tag == CP_METHOD_TYPE:
            return f"MethodType {self.get_utf8(entry[1])}"
        elif tag == CP_INVOKE_DYNAMIC:
            name, desc = self.get_name_and_type(entry[2])
            return f"InvokeDynamic bootstrap#{entry[1]} {name}:{desc}"
        return f"#{idx} tag={tag}"

    def parse_attributes(self, count):
        attrs = []
        for _ in range(count):
            name_idx = self.u2()
            length = self.u4()
            name = self.get_utf8(name_idx)
            data = self.read_bytes(length)
            attrs.append((name, data))
        return attrs

    def parse_code_attribute(self, data):
        """Parse a Code attribute."""
        p = 0
        max_stack = struct.unpack('>H', data[p:p+2])[0]; p += 2
        max_locals = struct.unpack('>H', data[p:p+2])[0]; p += 2
        code_length = struct.unpack('>I', data[p:p+4])[0]; p += 4
        code_bytes = data[p:p+code_length]; p += code_length

        # Exception table
        exc_count = struct.unpack('>H', data[p:p+2])[0]; p += 2
        exceptions = []
        for _ in range(exc_count):
            start_pc = struct.unpack('>H', data[p:p+2])[0]; p += 2
            end_pc = struct.unpack('>H', data[p:p+2])[0]; p += 2
            handler_pc = struct.unpack('>H', data[p:p+2])[0]; p += 2
            catch_type = struct.unpack('>H', data[p:p+2])[0]; p += 2
            catch_name = self.get_class_name(catch_type) if catch_type else "any"
            exceptions.append((start_pc, end_pc, handler_pc, catch_name))

        # Sub-attributes (LineNumberTable, LocalVariableTable, etc.)
        # We skip parsing sub-attributes for brevity

        return max_stack, max_locals, code_bytes, exceptions

    def parse(self):
        magic = self.u4()
        if magic != 0xCAFEBABE:
            return None

        minor = self.u2()
        major = self.u2()

        cp_count = self.u2()
        self.parse_cp(cp_count)

        access_flags = self.u2()
        this_class = self.u2()
        super_class = self.u2()

        # Interfaces
        iface_count = self.u2()
        interfaces = []
        for _ in range(iface_count):
            interfaces.append(self.u2())

        # Fields
        field_count = self.u2()
        fields = []
        for _ in range(field_count):
            f_access = self.u2()
            f_name_idx = self.u2()
            f_desc_idx = self.u2()
            f_attr_count = self.u2()
            f_attrs = self.parse_attributes(f_attr_count)
            fields.append((f_access, self.get_utf8(f_name_idx), self.get_utf8(f_desc_idx), f_attrs))

        # Methods
        method_count = self.u2()
        methods = []
        for _ in range(method_count):
            m_access = self.u2()
            m_name_idx = self.u2()
            m_desc_idx = self.u2()
            m_attr_count = self.u2()
            m_attrs = self.parse_attributes(m_attr_count)
            methods.append((m_access, self.get_utf8(m_name_idx), self.get_utf8(m_desc_idx), m_attrs))

        # Class attributes
        class_attr_count = self.u2()
        class_attrs = self.parse_attributes(class_attr_count)

        return {
            'version': (major, minor),
            'access_flags': access_flags,
            'this_class': self.get_class_name(this_class),
            'super_class': self.get_class_name(super_class) if super_class else "java.lang.Object",
            'interfaces': [self.get_class_name(i) for i in interfaces],
            'fields': fields,
            'methods': methods,
            'class_attrs': class_attrs,
        }

    def format_output(self, info):
        if not info:
            return "Failed to parse class file"

        lines = []

        # Class header
        flags = self.format_flags(info['access_flags'], 'class')
        lines.append(f"  {flags} class {info['this_class']} extends {info['super_class']}")
        if info['interfaces']:
            lines.append(f"  implements {', '.join(info['interfaces'])}")
        lines.append(f"  Version: {info['version'][0]}.{info['version'][1]}")
        lines.append("")

        # Fields
        if info['fields']:
            lines.append("  Fields:")
            for access, name, desc, attrs in info['fields']:
                ret, params = self.parse_descriptor(desc)
                flags = self.format_flags(access, 'field')
                lines.append(f"    {flags} {ret} {name}")
            lines.append("")

        # Methods
        if info['methods']:
            lines.append("  Methods:")
            for access, name, desc, attrs in info['methods']:
                ret, params = self.parse_descriptor(desc)
                flags = self.format_flags(access, 'method')
                param_str = ', '.join(params)
                lines.append(f"    {flags} {ret} {name}({param_str})")

                # Look for Code attribute
                for attr_name, attr_data in attrs:
                    if attr_name == 'Code':
                        max_stack, max_locals, code_bytes, exceptions = self.parse_code_attribute(attr_data)
                        lines.append(f"      max_stack={max_stack}, max_locals={max_locals}")
                        if exceptions:
                            lines.append(f"      Exception table:")
                            for start, end, handler, catch in exceptions:
                                lines.append(f"        {start}-{end} -> {handler} : {catch}")
                        lines.append(f"      Bytecode:")
                        bytecode_lines = self.disassemble(code_bytes)
                        for bline in bytecode_lines:
                            lines.append(f"    {bline}")
                    elif attr_name == 'Exceptions':
                        # Method throws clauses
                        p = 0
                        exc_count = struct.unpack('>H', attr_data[p:p+2])[0]; p += 2
                        for _ in range(exc_count):
                            exc_idx = struct.unpack('>H', attr_data[p:p+2])[0]; p += 2
                            lines.append(f"      throws {self.get_class_name(exc_idx)}")
                    elif attr_name == 'Signature':
                        sig_idx = struct.unpack('>H', attr_data[0:2])[0]
                        lines.append(f"      Signature: {self.get_utf8(sig_idx)}")
                    elif attr_name == 'RuntimeVisibleAnnotations':
                        lines.append(f"      Annotations: {len(attr_data)} bytes")
                    elif attr_name == 'AnnotationDefault':
                        lines.append(f"      AnnotationDefault: {len(attr_data)} bytes")

                lines.append("")

        return '\n'.join(lines)

    def format_flags(self, flags, kind):
        parts = []
        for bit, name in sorted(ACCESS_FLAGS.items()):
            if flags & bit:
                if kind == 'method' and bit == ACC_SUPER:
                    continue
                if kind == 'method' and bit == ACC_SYNCHRONIZED:
                    parts.append('synchronized')
                elif kind == 'field' and bit == ACC_BRIDGE:
                    continue
                else:
                    parts.append(name)
        return ' '.join(parts) if parts else 'package-private'


def parse_class_file(filepath):
    with open(filepath, 'rb') as f:
        data = f.read()
    parser = ClassFileParser(data)
    info = parser.parse()
    return parser.format_output(info)


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Usage: class_parser.py <class_file>")
        sys.exit(1)
    result = parse_class_file(sys.argv[1])
    print(result)
