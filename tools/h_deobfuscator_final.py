#!/usr/bin/env python3
"""
iFlyCode H() String Deobfuscator - Final Version

Algorithm: output[i] = input[i] XOR v[(len-i-1) % 106 + 1]
Each H() definition class has its own v[] sequence with period 106.

Usage: python3 h_deob_final.py [base_dir] [output_file]
"""
import struct
import os
import sys
import json
from collections import defaultdict

# v_map extracted from Java runtime — all 33 H() definition classes
V_MAP = {
    'com/aicode/util/AICodeStringUtil': [0, 49, 58, 44, 39, 48, 59, 48, 59, 38, 45, 32, 43, 32, 43, 2, 9, 38, 45, 47, 36, 39, 44, 45, 38, 34, 41, 11, 0, 39, 44, 44, 39, 43, 32, 55, 60, 38, 45, 14, 5, 55, 60, 32, 43, 38, 45, 49, 58, 42, 33, 7, 12, 109, 102, 55, 60, 32, 43, 38, 45, 47, 36, 37, 46, 38, 45, 49, 58, 109, 102, 47, 36, 34, 41, 45, 38, 49, 58, 38, 45, 55, 60, 45, 38, 42, 33, 109, 102, 40, 35, 39, 44, 41, 34, 38, 45, 40, 35, 44, 39, 53, 62, 45, 38, 42, 33],
    'com/aicode/diff/GenericUtils': [0, 91, 83, 85, 93, 81, 89, 72, 64, 80, 88, 87, 95, 76, 68, 81, 89, 77, 69, 77, 69, 91, 83, 93, 85, 93, 85, 127, 119, 91, 83, 82, 90, 90, 82, 80, 88, 95, 87, 118, 126, 90, 82, 81, 89, 86, 94, 74, 66, 91, 83, 115, 123, 74, 66, 93, 85, 91, 83, 76, 68, 87, 95, 122, 114, 16, 24, 74, 66, 93, 85, 91, 83, 82, 90, 88, 80, 91, 83, 76, 68, 16, 24, 82, 90, 95, 87, 80, 88, 76, 68, 91, 83, 74, 66, 80, 88, 87, 95, 16, 24, 85, 93, 90, 82, 84, 92],
    'com/aicode/util/NewFileUtils': [0, 121, 43, 119, 37, 115, 33, 106, 56, 114, 32, 117, 39, 110, 60, 115, 33, 111, 61, 111, 61, 121, 43, 127, 45, 127, 45, 93, 15, 121, 43, 112, 34, 120, 42, 114, 32, 125, 47, 84, 6, 120, 42, 115, 33, 116, 38, 104, 58, 121, 43, 81, 3, 104, 58, 127, 45, 121, 43, 110, 60, 117, 39, 88, 10, 50, 96, 104, 58, 127, 45, 121, 43, 112, 34, 122, 40, 121, 43, 110, 60, 50, 96, 112, 34, 125, 47, 114, 32, 110, 60, 121, 43, 104, 58, 114, 32, 117, 39, 50, 96, 119, 37, 120, 42, 118, 36],
    'com/aicode/util/PropertyUtils': [0, 7, 80, 26, 77, 6, 81, 6, 81, 16, 71, 22, 65, 22, 65, 52, 99, 16, 71, 25, 78, 17, 70, 27, 76, 20, 67, 61, 106, 17, 70, 26, 77, 29, 74, 1, 86, 16, 71, 56, 111, 1, 86, 22, 65, 16, 71, 7, 80, 28, 75, 49, 102, 91, 12, 1, 86, 22, 65, 16, 71, 25, 78, 19, 68, 16, 71, 7, 80, 91, 12, 25, 78, 20, 67, 27, 76, 7, 80, 16, 71, 1, 86, 27, 76, 28, 75, 91, 12, 30, 73, 17, 70, 31, 72, 16, 71, 30, 73, 26, 77, 3, 84, 27, 76, 28, 75],
    'com/aicode/ui/FontKt': [0, 78, 82, 64, 92, 68, 88, 93, 65, 69, 89, 66, 94, 89, 69, 68, 88, 88, 68, 88, 68, 78, 82, 72, 84, 72, 84, 106, 118, 78, 82, 71, 91, 79, 83, 69, 89, 74, 86, 99, 127, 79, 83, 68, 88, 67, 95, 95, 67, 78, 82, 102, 122, 95, 67, 72, 84, 78, 82, 89, 69, 66, 94, 111, 115, 5, 25, 95, 67, 72, 84, 78, 82, 71, 91, 77, 81, 78, 82, 89, 69, 5, 25, 71, 91, 74, 86, 69, 89, 89, 69, 78, 82, 95, 67, 69, 89, 66, 94, 5, 25, 64, 92, 79, 83, 65, 93],
    'com/aicode/util/HandleCacheUtil': [0, 72, 23, 85, 10, 73, 22, 73, 22, 95, 0, 89, 6, 89, 6, 123, 36, 95, 0, 86, 9, 94, 1, 84, 11, 91, 4, 114, 45, 94, 1, 85, 10, 82, 13, 78, 17, 95, 0, 119, 40, 78, 17, 89, 6, 95, 0, 72, 23, 83, 12, 126, 33, 20, 75, 78, 17, 89, 6, 95, 0, 86, 9, 92, 3, 95, 0, 72, 23, 20, 75, 86, 9, 91, 4, 84, 11, 72, 23, 95, 0, 78, 17, 84, 11, 83, 12, 20, 75, 81, 14, 94, 1, 80, 15, 95, 0, 81, 14, 85, 10, 76, 19, 84, 11, 83, 12],
    'com/aicode/util/IndentLineUtil': [0, 55, 104, 42, 117, 54, 105, 54, 105, 32, 127, 38, 121, 38, 121, 4, 91, 32, 127, 41, 118, 33, 126, 43, 116, 36, 123, 13, 82, 33, 126, 42, 117, 45, 114, 49, 110, 32, 127, 8, 87, 49, 110, 38, 121, 32, 127, 55, 104, 44, 115, 1, 94, 107, 52, 49, 110, 38, 121, 32, 127, 41, 118, 35, 124, 32, 127, 55, 104, 107, 52, 41, 118, 36, 123, 43, 116, 55, 104, 32, 127, 49, 110, 43, 116, 44, 115, 107, 52, 46, 113, 33, 126, 47, 112, 32, 127, 46, 113, 42, 117, 51, 108, 43, 116, 44, 115],
    'com/aicode/content/util/EditorUtils': [0, 90, 1, 71, 28, 91, 0, 91, 0, 77, 22, 75, 16, 75, 16, 105, 50, 77, 22, 68, 31, 76, 23, 70, 29, 73, 18, 96, 59, 76, 23, 71, 28, 64, 27, 92, 7, 77, 22, 101, 62, 92, 7, 75, 16, 77, 22, 90, 1, 65, 26, 108, 55, 6, 93, 92, 7, 75, 16, 77, 22, 68, 31, 78, 21, 77, 22, 90, 1, 6, 93, 68, 31, 73, 18, 70, 29, 90, 1, 77, 22, 92, 7, 70, 29, 65, 26, 6, 93, 67, 24, 76, 23, 66, 25, 77, 22, 67, 24, 71, 28, 94, 5, 70, 29, 65, 26],
    'com/aicode/exception/RequestCancelException': [0, 83, 52, 93, 58, 89, 62, 64, 39, 88, 63, 95, 56, 68, 35, 89, 62, 69, 34, 69, 34, 83, 52, 85, 50, 85, 50, 119, 16, 83, 52, 90, 61, 82, 53, 88, 63, 87, 48, 126, 25, 82, 53, 89, 62, 94, 57, 66, 37, 83, 52, 123, 28, 66, 37, 85, 50, 83, 52, 68, 35, 95, 56, 114, 21, 24, 127, 66, 37, 85, 50, 83, 52, 90, 61, 80, 55, 83, 52, 68, 35, 24, 127, 90, 61, 87, 48, 88, 63, 68, 35, 83, 52, 66, 37, 88, 63, 95, 56, 24, 127, 93, 58, 82, 53, 92, 59],
    'com/aicode/util/Maps': [0, 88, 100, 86, 106, 82, 110, 75, 119, 83, 111, 84, 104, 79, 115, 82, 110, 78, 114, 78, 114, 88, 100, 94, 98, 94, 98, 124, 64, 88, 100, 81, 109, 89, 101, 83, 111, 92, 96, 117, 73, 89, 101, 82, 110, 85, 105, 73, 117, 88, 100, 112, 76, 73, 117, 94, 98, 88, 100, 79, 115, 84, 104, 121, 69, 19, 47, 73, 117, 94, 98, 88, 100, 81, 109, 91, 103, 88, 100, 79, 115, 19, 47, 81, 109, 92, 96, 83, 111, 79, 115, 88, 100, 73, 117, 83, 111, 84, 104, 19, 47, 86, 106, 89, 101, 87, 107],
    'com/aicode/agent/service/CodeCompleteService': [0, 12, 41, 2, 39, 6, 35, 31, 58, 7, 34, 0, 37, 27, 62, 6, 35, 26, 63, 26, 63, 12, 41, 10, 47, 10, 47, 40, 13, 12, 41, 5, 32, 13, 40, 7, 34, 8, 45, 33, 4, 13, 40, 6, 35, 1, 36, 29, 56, 12, 41, 36, 1, 29, 56, 10, 47, 12, 41, 27, 62, 0, 37, 45, 8, 71, 98, 29, 56, 10, 47, 12, 41, 5, 32, 15, 42, 12, 41, 27, 62, 71, 98, 5, 32, 8, 45, 7, 34, 27, 62, 12, 41, 29, 56, 7, 34, 0, 37, 71, 98, 2, 39, 13, 40, 3, 38],
    'com/aicode/service/editor/RequestResultList': [0, 52, 6, 41, 27, 53, 7, 53, 7, 35, 17, 37, 23, 37, 23, 7, 53, 35, 17, 42, 24, 34, 16, 40, 26, 39, 21, 14, 60, 34, 16, 41, 27, 46, 28, 50, 0, 35, 17, 11, 57, 50, 0, 37, 23, 35, 17, 52, 6, 47, 29, 2, 48, 104, 90, 50, 0, 37, 23, 35, 17, 42, 24, 32, 18, 35, 17, 52, 6, 104, 90, 42, 24, 39, 21, 40, 26, 52, 6, 35, 17, 50, 0, 40, 26, 47, 29, 104, 90, 45, 31, 34, 16, 44, 30, 35, 17, 45, 31, 41, 27, 48, 2, 40, 26, 47, 29],
    'com/aicode/util/JComponentKt': [0, 111, 73, 97, 71, 101, 67, 124, 90, 100, 66, 99, 69, 120, 94, 101, 67, 121, 95, 121, 95, 111, 73, 105, 79, 105, 79, 75, 109, 111, 73, 102, 64, 110, 72, 100, 66, 107, 77, 66, 100, 110, 72, 101, 67, 98, 68, 126, 88, 111, 73, 71, 97, 126, 88, 105, 79, 111, 73, 120, 94, 99, 69, 78, 104, 36, 2, 126, 88, 105, 79, 111, 73, 102, 64, 108, 74, 111, 73, 120, 94, 36, 2, 102, 64, 107, 77, 100, 66, 120, 94, 111, 73, 126, 88, 100, 66, 99, 69, 36, 2, 97, 71, 110, 72, 96, 70],
    'com/aicode/service/editor/CancelRequestTip': [0, 97, 97, 111, 111, 107, 107, 114, 114, 106, 106, 109, 109, 118, 118, 107, 107, 119, 119, 119, 119, 97, 97, 103, 103, 103, 103, 69, 69, 97, 97, 104, 104, 96, 96, 106, 106, 101, 101, 76, 76, 96, 96, 107, 107, 108, 108, 112, 112, 97, 97, 73, 73, 112, 112, 103, 103, 97, 97, 118, 118, 109, 109, 64, 64, 42, 42, 112, 112, 103, 103, 97, 97, 104, 104, 98, 98, 97, 97, 118, 118, 42, 42, 104, 104, 101, 101, 106, 106, 118, 118, 97, 97, 112, 112, 106, 106, 109, 109, 42, 42, 111, 111, 96, 96, 110, 110],
    'com/aicode/content/util/file/FileExtensionLanguageDetails': [0, 0, 18, 29, 15, 1, 19, 1, 19, 23, 5, 17, 3, 17, 3, 51, 33, 23, 5, 30, 12, 22, 4, 28, 14, 19, 1, 58, 40, 22, 4, 29, 15, 26, 8, 6, 20, 23, 5, 63, 45, 6, 20, 17, 3, 23, 5, 0, 18, 27, 9, 54, 36, 92, 78, 6, 20, 17, 3, 23, 5, 30, 12, 20, 6, 23, 5, 0, 18, 92, 78, 30, 12, 19, 1, 28, 14, 0, 18, 23, 5, 6, 20, 28, 14, 27, 9, 92, 78, 25, 11, 22, 4, 24, 10, 23, 5, 25, 11, 29, 15, 4, 22, 28, 14, 27, 9],
    'com/aicode/action/batch/MethodGeneratorConfig': [0, 95, 81, 81, 95, 85, 91, 76, 66, 84, 90, 83, 93, 72, 70, 85, 91, 73, 71, 73, 71, 95, 81, 89, 87, 89, 87, 123, 117, 95, 81, 86, 88, 94, 80, 84, 90, 91, 85, 114, 124, 94, 80, 85, 91, 82, 92, 78, 64, 95, 81, 119, 121, 78, 64, 89, 87, 95, 81, 72, 70, 83, 93, 126, 112, 20, 26, 78, 64, 89, 87, 95, 81, 86, 88, 92, 82, 95, 81, 72, 70, 20, 26, 86, 88, 91, 85, 84, 90, 72, 70, 95, 81, 78, 64, 84, 90, 83, 93, 20, 26, 81, 95, 94, 80, 80, 94],
    'com/aicode/apm/OpenTelemetryUtil': [0, 87, 19, 74, 14, 86, 18, 86, 18, 64, 4, 70, 2, 70, 2, 100, 32, 64, 4, 73, 13, 65, 5, 75, 15, 68, 0, 109, 41, 65, 5, 74, 14, 77, 9, 81, 21, 64, 4, 104, 44, 81, 21, 70, 2, 64, 4, 87, 19, 76, 8, 97, 37, 11, 79, 81, 21, 70, 2, 64, 4, 73, 13, 67, 7, 64, 4, 87, 19, 11, 79, 73, 13, 68, 0, 75, 15, 87, 19, 64, 4, 81, 21, 75, 15, 76, 8, 11, 79, 78, 10, 65, 5, 79, 11, 64, 4, 78, 10, 74, 14, 83, 23, 75, 15, 76, 8],
    'com/aicode/util/AICodeUtils': [0, 19, 29, 14, 0, 18, 28, 18, 28, 4, 10, 2, 12, 2, 12, 32, 46, 4, 10, 13, 3, 5, 11, 15, 1, 0, 14, 41, 39, 5, 11, 14, 0, 9, 7, 21, 27, 4, 10, 44, 34, 21, 27, 2, 12, 4, 10, 19, 29, 8, 6, 37, 43, 79, 65, 21, 27, 2, 12, 4, 10, 13, 3, 7, 9, 4, 10, 19, 29, 79, 65, 13, 3, 0, 14, 15, 1, 19, 29, 4, 10, 21, 27, 15, 1, 8, 6, 79, 65, 10, 4, 5, 11, 11, 5, 4, 10, 10, 4, 14, 0, 23, 25, 15, 1, 8, 6],
    'com/aicode/inline/status/InlineChatStatusServiceKt': [0, 127, 90, 98, 71, 126, 91, 126, 91, 104, 77, 110, 75, 110, 75, 76, 105, 104, 77, 97, 68, 105, 76, 99, 70, 108, 73, 69, 96, 105, 76, 98, 71, 101, 64, 121, 92, 104, 77, 64, 101, 121, 92, 110, 75, 104, 77, 127, 90, 100, 65, 73, 108, 35, 6, 121, 92, 110, 75, 104, 77, 97, 68, 107, 78, 104, 77, 127, 90, 35, 6, 97, 68, 108, 73, 99, 70, 127, 90, 104, 77, 121, 92, 99, 70, 100, 65, 35, 6, 102, 67, 105, 76, 103, 66, 104, 77, 102, 67, 98, 71, 123, 94, 99, 70, 100, 65],
    'com/aicode/language/AICodeLanguageInfo': [0, 126, 79, 112, 65, 116, 69, 109, 92, 117, 68, 114, 67, 105, 88, 116, 69, 104, 89, 104, 89, 126, 79, 120, 73, 120, 73, 90, 107, 126, 79, 119, 70, 127, 78, 117, 68, 122, 75, 83, 98, 127, 78, 116, 69, 115, 66, 111, 94, 126, 79, 86, 103, 111, 94, 120, 73, 126, 79, 105, 88, 114, 67, 95, 110, 53, 4, 111, 94, 120, 73, 126, 79, 119, 70, 125, 76, 126, 79, 105, 88, 53, 4, 119, 70, 122, 75, 117, 68, 105, 88, 126, 79, 111, 94, 117, 68, 114, 67, 53, 4, 112, 65, 127, 78, 113, 64],
    'com/aicode/agent/service/GitReviewService': [0, 122, 65, 116, 79, 112, 75, 105, 82, 113, 74, 118, 77, 109, 86, 112, 75, 108, 87, 108, 87, 122, 65, 124, 71, 124, 71, 94, 101, 122, 65, 115, 72, 123, 64, 113, 74, 126, 69, 87, 108, 123, 64, 112, 75, 119, 76, 107, 80, 122, 65, 82, 105, 107, 80, 124, 71, 122, 65, 109, 86, 118, 77, 91, 96, 49, 10, 107, 80, 124, 71, 122, 65, 115, 72, 121, 66, 122, 65, 109, 86, 49, 10, 115, 72, 126, 69, 113, 74, 109, 86, 122, 65, 107, 80, 113, 74, 118, 77, 49, 10, 116, 79, 123, 64, 117, 78],
    'com/aicode/util/PositionUtil': [0, 73, 57, 71, 55, 67, 51, 90, 42, 66, 50, 69, 53, 94, 46, 67, 51, 95, 47, 95, 47, 73, 57, 79, 63, 79, 63, 109, 29, 73, 57, 64, 48, 72, 56, 66, 50, 77, 61, 100, 20, 72, 56, 67, 51, 68, 52, 88, 40, 73, 57, 97, 17, 88, 40, 79, 63, 73, 57, 94, 46, 69, 53, 104, 24, 2, 114, 88, 40, 79, 63, 73, 57, 64, 48, 74, 58, 73, 57, 94, 46, 2, 114, 64, 48, 77, 61, 66, 50, 94, 46, 73, 57, 88, 40, 66, 50, 69, 53, 2, 114, 71, 55, 72, 56, 70, 54],
    'com/aicode/diff/FileService': [0, 81, 67, 76, 94, 80, 66, 80, 66, 70, 84, 64, 82, 64, 82, 98, 112, 70, 84, 79, 93, 71, 85, 77, 95, 66, 80, 107, 121, 71, 85, 76, 94, 75, 89, 87, 69, 70, 84, 110, 124, 87, 69, 64, 82, 70, 84, 81, 67, 74, 88, 103, 117, 13, 31, 87, 69, 64, 82, 70, 84, 79, 93, 69, 87, 70, 84, 81, 67, 13, 31, 79, 93, 66, 80, 77, 95, 81, 67, 70, 84, 87, 69, 77, 95, 74, 88, 13, 31, 72, 90, 71, 85, 73, 91, 70, 84, 72, 90, 76, 94, 85, 71, 77, 95, 74, 88],
    'com/aicode/util/Application': [0, 17, 18, 12, 15, 16, 19, 16, 19, 6, 5, 0, 3, 0, 3, 34, 33, 6, 5, 15, 12, 7, 4, 13, 14, 2, 1, 43, 40, 7, 4, 12, 15, 11, 8, 23, 20, 6, 5, 46, 45, 23, 20, 0, 3, 6, 5, 17, 18, 10, 9, 39, 36, 77, 78, 23, 20, 0, 3, 6, 5, 15, 12, 5, 6, 6, 5, 17, 18, 77, 78, 15, 12, 2, 1, 13, 14, 17, 18, 6, 5, 23, 20, 13, 14, 10, 9, 77, 78, 8, 11, 7, 4, 9, 10, 6, 5, 8, 11, 12, 15, 21, 22, 13, 14, 10, 9],
    'com/aicode/inline/ide/IdeAction': [0, 51, 120, 46, 101, 50, 121, 50, 121, 36, 111, 34, 105, 34, 105, 0, 75, 36, 111, 45, 102, 37, 110, 47, 100, 32, 107, 9, 66, 37, 110, 46, 101, 41, 98, 53, 126, 36, 111, 12, 71, 53, 126, 34, 105, 36, 111, 51, 120, 40, 99, 5, 78, 111, 36, 53, 126, 34, 105, 36, 111, 45, 102, 39, 108, 36, 111, 51, 120, 111, 36, 45, 102, 32, 107, 47, 100, 51, 120, 36, 111, 53, 126, 47, 100, 40, 99, 111, 36, 42, 97, 37, 110, 43, 96, 36, 111, 42, 97, 46, 101, 55, 124, 47, 100, 40, 99],
    'com/aicode/action/batch/GeneratorConfig': [0, 111, 121, 114, 100, 110, 120, 110, 120, 120, 110, 126, 104, 126, 104, 92, 74, 120, 110, 113, 103, 121, 111, 115, 101, 124, 106, 85, 67, 121, 111, 114, 100, 117, 99, 105, 127, 120, 110, 80, 70, 105, 127, 126, 104, 120, 110, 111, 121, 116, 98, 89, 79, 51, 37, 105, 127, 126, 104, 120, 110, 113, 103, 123, 109, 120, 110, 111, 121, 51, 37, 113, 103, 124, 106, 115, 101, 111, 121, 120, 110, 105, 127, 115, 101, 116, 98, 51, 37, 118, 96, 121, 111, 119, 97, 120, 110, 118, 96, 114, 100, 107, 125, 115, 101, 116, 98],
    'com/aicode/inline/ide/ConditionalActionConfiguration': [0, 125, 120, 115, 118, 119, 114, 110, 107, 118, 115, 113, 116, 106, 111, 119, 114, 107, 110, 107, 110, 125, 120, 123, 126, 123, 126, 89, 92, 125, 120, 116, 113, 124, 121, 118, 115, 121, 124, 80, 85, 124, 121, 119, 114, 112, 117, 108, 105, 125, 120, 85, 80, 108, 105, 123, 126, 125, 120, 106, 111, 113, 116, 92, 89, 54, 51, 108, 105, 123, 126, 125, 120, 116, 113, 126, 123, 125, 120, 106, 111, 54, 51, 116, 113, 121, 124, 118, 115, 106, 111, 125, 120, 108, 105, 118, 115, 113, 116, 54, 51, 115, 118, 124, 121, 114, 119],
    'com/aicode/exception/RequestTimeoutException': [0, 102, 71, 123, 90, 103, 70, 103, 70, 113, 80, 119, 86, 119, 86, 85, 116, 113, 80, 120, 89, 112, 81, 122, 91, 117, 84, 92, 125, 112, 81, 123, 90, 124, 93, 96, 65, 113, 80, 89, 120, 96, 65, 119, 86, 113, 80, 102, 71, 125, 92, 80, 113, 58, 27, 96, 65, 119, 86, 113, 80, 120, 89, 114, 83, 113, 80, 102, 71, 58, 27, 120, 89, 117, 84, 122, 91, 102, 71, 113, 80, 96, 65, 122, 91, 125, 92, 58, 27, 127, 94, 112, 81, 126, 95, 113, 80, 127, 94, 123, 90, 98, 67, 122, 91, 125, 92],
    'com/aicode/inline/controller/ChatInputController': [0, 126, 114, 112, 124, 116, 120, 109, 97, 117, 121, 114, 126, 105, 101, 116, 120, 104, 100, 104, 100, 126, 114, 120, 116, 120, 116, 90, 86, 126, 114, 119, 123, 127, 115, 117, 121, 122, 118, 83, 95, 127, 115, 116, 120, 115, 127, 111, 99, 126, 114, 86, 90, 111, 99, 120, 116, 126, 114, 105, 101, 114, 126, 95, 83, 53, 57, 111, 99, 120, 116, 126, 114, 119, 123, 125, 113, 126, 114, 105, 101, 53, 57, 119, 123, 122, 118, 117, 121, 105, 101, 126, 114, 111, 99, 117, 121, 114, 126, 53, 57, 112, 124, 127, 115, 113, 125],
    'com/aicode/content/util/OverlayUtils': [0, 119, 90, 106, 71, 118, 91, 118, 91, 96, 77, 102, 75, 102, 75, 68, 105, 96, 77, 105, 68, 97, 76, 107, 70, 100, 73, 77, 96, 97, 76, 106, 71, 109, 64, 113, 92, 96, 77, 72, 101, 113, 92, 102, 75, 96, 77, 119, 90, 108, 65, 65, 108, 43, 6, 113, 92, 102, 75, 96, 77, 105, 68, 99, 78, 96, 77, 119, 90, 43, 6, 105, 68, 100, 73, 107, 70, 119, 90, 96, 77, 113, 92, 107, 70, 108, 65, 43, 6, 110, 67, 97, 76, 111, 66, 96, 77, 110, 67, 106, 71, 115, 94, 107, 70, 108, 65],
    'com/aicode/content/util/file/LanguageFileExtensionDetails': [0, 127, 18, 98, 15, 126, 19, 126, 19, 104, 5, 110, 3, 110, 3, 76, 33, 104, 5, 97, 12, 105, 4, 99, 14, 108, 1, 69, 40, 105, 4, 98, 15, 101, 8, 121, 20, 104, 5, 64, 45, 121, 20, 110, 3, 104, 5, 127, 18, 100, 9, 73, 36, 35, 78, 121, 20, 110, 3, 104, 5, 97, 12, 107, 6, 104, 5, 127, 18, 35, 78, 97, 12, 108, 1, 99, 14, 127, 18, 104, 5, 121, 20, 99, 14, 100, 9, 35, 78, 102, 11, 105, 4, 103, 10, 104, 5, 102, 11, 98, 15, 123, 22, 99, 14, 100, 9],
    'com/aicode/ui/ActionButton': [0, 105, 115, 116, 110, 104, 114, 104, 114, 126, 100, 120, 98, 120, 98, 90, 64, 126, 100, 119, 109, 127, 101, 117, 111, 122, 96, 83, 73, 127, 101, 116, 110, 115, 105, 111, 117, 126, 100, 86, 76, 111, 117, 120, 98, 126, 100, 105, 115, 114, 104, 95, 69, 53, 47, 111, 117, 120, 98, 126, 100, 119, 109, 125, 103, 126, 100, 105, 115, 53, 47, 119, 109, 122, 96, 117, 111, 105, 115, 126, 100, 111, 117, 117, 111, 114, 104, 53, 47, 112, 106, 127, 101, 113, 107, 126, 100, 112, 106, 116, 110, 109, 119, 117, 111, 114, 104],
    'com/aicode/diff/FileInfo': [0, 0, 105, 29, 116, 1, 104, 1, 104, 23, 126, 17, 120, 17, 120, 51, 90, 23, 126, 30, 119, 22, 127, 28, 117, 19, 122, 58, 83, 22, 127, 29, 116, 26, 115, 6, 111, 23, 126, 63, 86, 6, 111, 17, 120, 23, 126, 0, 105, 27, 114, 54, 95, 92, 53, 6, 111, 17, 120, 23, 126, 30, 119, 20, 125, 23, 126, 0, 105, 92, 53, 30, 119, 19, 122, 28, 117, 0, 105, 23, 126, 6, 111, 28, 117, 27, 114, 92, 53, 25, 112, 22, 127, 24, 113, 23, 126, 25, 112, 29, 116, 4, 109, 28, 117, 27, 114],
}


OPCODE_LEN = {
    0x00:1,0x01:1,0x02:1,0x03:1,0x04:1,0x05:1,0x06:1,0x07:1,0x08:1,
    0x09:1,0x0A:1,0x0B:1,0x0C:1,0x0D:1,0x0E:1,0x0F:1,0x10:1,
    0x11:2,0x12:2,0x13:3,0x14:3,0x15:2,0x16:2,0x17:2,0x18:2,0x19:2,
    0x1A:1,0x1B:1,0x1C:1,0x1D:1,0x1E:1,0x1F:1,0x20:1,0x21:1,
    0x22:1,0x23:1,0x24:1,0x25:1,0x26:1,0x27:1,0x28:1,0x29:1,
    0x2A:1,0x2B:1,0x2C:1,0x2D:1,0x2E:1,0x2F:1,0x30:1,0x31:1,
    0x32:1,0x33:1,0x34:1,0x35:1,0x36:2,0x37:2,0x38:2,0x39:2,0x3A:2,
    0x3B:1,0x3C:1,0x3D:1,0x3E:1,0x3F:1,0x40:1,0x41:1,0x42:1,
    0x43:1,0x44:1,0x45:1,0x46:1,0x47:1,0x48:1,0x49:1,0x4A:1,
    0x4B:1,0x4C:1,0x4D:1,0x4E:1,0x4F:1,0x50:1,0x51:1,0x52:1,
    0x53:1,0x54:1,0x55:1,0x56:1,0x57:1,0x58:1,0x59:1,0x5A:1,
    0x5B:1,0x5C:1,0x5D:1,0x5E:1,0x5F:1,0x60:1,0x61:1,0x62:1,
    0x63:1,0x64:1,0x65:1,0x66:1,0x67:1,0x68:1,0x69:1,0x6A:1,
    0x6B:1,0x6C:1,0x6D:1,0x6E:1,0x6F:1,0x70:1,0x71:1,0x72:1,
    0x73:1,0x74:1,0x75:1,0x76:1,0x77:1,0x78:1,0x79:1,0x7A:1,
    0x7B:1,0x7C:1,0x7D:1,0x7E:1,0x7F:1,0x80:1,0x81:1,0x82:1,
    0x83:1,0x84:3,0x85:1,0x86:1,0x87:1,0x88:1,0x89:1,0x8A:1,
    0x8B:1,0x8C:1,0x8D:1,0x8E:1,0x8F:1,0x90:1,0x91:1,0x92:1,
    0x93:1,0x94:1,0x95:1,0x96:1,0x97:1,0x98:1,
    0x99:3,0x9A:3,0x9B:3,0x9C:3,0x9D:3,0x9E:3,0x9F:3,
    0xA0:3,0xA1:3,0xA2:3,0xA3:3,0xA4:3,0xA5:3,0xA6:3,
    0xA7:3,0xA8:3,0xA9:2,
    0xAC:1,0xAD:1,0xAE:1,0xAF:1,0xB0:1,0xB1:1,
    0xB2:2,0xB3:2,0xB4:2,0xB5:2,0xB6:3,0xB7:3,0xB8:3,0xB9:5,0xBA:5,
    0xBB:3,0xBC:2,0xBD:3,0xBE:1,0xBF:1,0xC0:3,0xC1:3,0xC2:1,0xC3:1,
    0xC5:4,0xC6:3,0xC7:3,0xC8:5,0xC9:5,
}

def parse_cp(data):
    pos = 8
    cp_count = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
    constants = [None]; utf8_map = {}; i = 1
    while i < cp_count:
        if pos >= len(data): break
        tag = data[pos]; pos += 1
        if tag == 1:
            l = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
            v = data[pos:pos+l].decode('utf-8', errors='replace'); pos += l
            constants.append(('Utf8', v)); utf8_map[i] = v
        elif tag == 7: ni = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2; constants.append(('Class', ni))
        elif tag == 8: si = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2; constants.append(('String', si))
        elif tag == 10: ci = struct.unpack('>H', data[pos:pos+2])[0]; ni = struct.unpack('>H', data[pos+2:pos+4])[0]; pos += 4; constants.append(('Methodref', (ci, ni)))
        elif tag == 12: ni = struct.unpack('>H', data[pos:pos+2])[0]; di = struct.unpack('>H', data[pos+2:pos+4])[0]; pos += 4; constants.append(('NameAndType', (ni, di)))
        elif tag in (3,4): pos += 4; constants.append(('Num', None))
        elif tag in (5,6): pos += 8; constants.append(('Wide', None)); constants.append(None); i += 1
        elif tag == 9: pos += 4; constants.append(('Fieldref', None))
        elif tag == 11: pos += 4; constants.append(('IFMethodref', None))
        elif tag == 15: pos += 3; constants.append(('MH', None))
        elif tag == 16: pos += 2; constants.append(('MT', None))
        elif tag in (17,18): pos += 4; constants.append(('Dyn', None))
        elif tag in (19,20): pos += 2; constants.append(('Mod', None))
        else: break
        i += 1
    return constants, utf8_map, pos

def resolve_utf8(constants, idx):
    if 0 < idx < len(constants) and constants[idx] and constants[idx][0] == 'Utf8':
        return constants[idx][1]
    return None

def resolve_class(constants, idx):
    if 0 < idx < len(constants) and constants[idx] and constants[idx][0] == 'Class':
        return resolve_utf8(constants, constants[idx][1])
    return None

def resolve_methodref(constants, idx):
    if 0 < idx < len(constants) and constants[idx] and constants[idx][0] == 'Methodref':
        ci, ni = constants[idx][1]
        cn = resolve_class(constants, ci)
        if ni < len(constants) and constants[ni] and constants[ni][0] == 'NameAndType':
            name_idx, desc_idx = constants[ni][1]
            return (cn, resolve_utf8(constants, name_idx), resolve_utf8(constants, desc_idx))
    return None

def find_h_calls_in_bytecode(bytecode, constants):
    results = []
    pos = 0; last_ldc_string = None; blen = len(bytecode)
    while 0 <= pos < blen:
        op = bytecode[pos]
        if op == 0x12:  # ldc
            if pos+1 >= blen: break
            idx = bytecode[pos+1]
            if 0 < idx < len(constants) and constants[idx]:
                c = constants[idx]
                if c[0] == 'String': last_ldc_string = resolve_utf8(constants, c[1])
            pos += 2
        elif op == 0x13:  # ldc_w
            if pos+2 >= blen: break
            idx = struct.unpack('>H', bytecode[pos+1:pos+3])[0]
            if 0 < idx < len(constants) and constants[idx]:
                c = constants[idx]
                if c[0] == 'String': last_ldc_string = resolve_utf8(constants, c[1])
            pos += 3
        elif op == 0xB8:  # invokestatic
            if pos+2 >= blen: break
            idx = struct.unpack('>H', bytecode[pos+1:pos+3])[0]
            ref = resolve_methodref(constants, idx)
            if ref and ref[1] == 'H':
                results.append({'obfuscated': last_ldc_string, 'target_class': ref[0]})
            last_ldc_string = None; pos += 3
        elif op in (0xB6, 0xB7):  # invokevirtual / invokespecial
            if pos+2 >= blen: break
            idx = struct.unpack('>H', bytecode[pos+1:pos+3])[0]
            ref = resolve_methodref(constants, idx)
            if ref and ref[1] == 'H':
                results.append({'obfuscated': last_ldc_string, 'target_class': ref[0]})
            last_ldc_string = None; pos += 3
        elif op == 0xB9: pos += 5; last_ldc_string = None
        elif op == 0xBA: pos += 5; last_ldc_string = None
        elif op == 0xAA:  # tableswitch
            pad = (4 - ((pos+1) % 4)) % 4; base = pos + 1 + pad
            if base + 12 > blen: break
            low = struct.unpack('>i', bytecode[base+4:base+8])[0]
            high = struct.unpack('>i', bytecode[base+8:base+12])[0]
            pos = base + 12 + (high - low + 1) * 4; last_ldc_string = None
        elif op == 0xAB:  # lookupswitch
            pad = (4 - ((pos+1) % 4)) % 4; base = pos + 1 + pad
            if base + 8 > blen: break
            npairs = struct.unpack('>i', bytecode[base+4:base+8])[0]
            pos = base + 8 + npairs * 8; last_ldc_string = None
        elif op == 0xC4:  # wide
            if pos+1 < blen: pos += 6 if bytecode[pos+1] == 0x84 else 4
            else: pos += 1
            last_ldc_string = None
        else:
            length = OPCODE_LEN.get(op, 1)
            if length < 1: length = 1
            pos += length
            if op not in (0x12, 0x13): last_ldc_string = None
    return results

def is_printable_char(c):
    """Check if a character is 'printable' — includes CJK, common symbols, etc."""
    cp = ord(c)
    if 32 <= cp < 127: return True
    if 0x4E00 <= cp <= 0x9FFF: return True  # CJK Unified Ideographs
    if 0x3400 <= cp <= 0x4DBF: return True  # CJK Extension A
    if 0x20000 <= cp <= 0x2A6DF: return True  # CJK Extension B
    if 0x3000 <= cp <= 0x303F: return True  # CJK punctuation
    if 0xFF00 <= cp <= 0xFFEF: return True  # Fullwidth forms
    if cp in (0x00A9, 0x00AE, 0x2122): return True
    if 0x00A0 <= cp <= 0x00FF: return True  # Latin-1 Supplement
    if 0x2000 <= cp <= 0x206F: return True  # General punctuation
    if 0x20A0 <= cp <= 0x20CF: return True  # Currency symbols
    if 0x2100 <= cp <= 0x214F: return True  # Letterlike symbols
    if 0x2190 <= cp <= 0x21FF: return True  # Arrows
    if 0x2200 <= cp <= 0x22FF: return True  # Mathematical operators
    if 0x2500 <= cp <= 0x257F: return True  # Box drawing
    if 0x2600 <= cp <= 0x26FF: return True  # Misc symbols
    return False

def classify_quality(decoded):
    """Classify decoded string quality: high/medium/low/garbage"""
    if not decoded: return 'garbage', 0.0
    printable = sum(1 for c in decoded if is_printable_char(c))
    ratio = printable / len(decoded)
    control_chars = sum(1 for c in decoded if ord(c) < 32 and c not in '\t\n\r')
    control_ratio = control_chars / len(decoded)
    if control_ratio > 0.3: return 'garbage', ratio
    if ratio >= 0.8: return 'high', ratio
    if ratio >= 0.5: return 'medium', ratio
    if ratio >= 0.3: return 'low', ratio
    return 'garbage', ratio

def xor_decode(obfuscated, v_seq):
    """Decode: output[i] = input[i] XOR v[(len-i-1) % 106 + 1]"""
    if not obfuscated or not v_seq: return None
    length = len(obfuscated)
    result = []
    for i, c in enumerate(obfuscated):
        v_idx = ((length - i - 1) % 106) + 1
        result.append(chr(ord(c) ^ v_seq[v_idx]))
    return ''.join(result)

def analyze_class(filepath):
    try:
        with open(filepath, 'rb') as f: data = f.read()
        if len(data) < 10 or struct.unpack('>I', data[:4])[0] != 0xCAFEBABE: return None
    except: return None
    constants, utf8_map, pos_after_cp = parse_cp(data)
    this_class_idx = struct.unpack('>H', data[pos_after_cp+2:pos_after_cp+4])[0]
    class_name = resolve_class(constants, this_class_idx)
    pos = pos_after_cp + 6
    ic = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2 + ic*2
    fc = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
    for _ in range(fc):
        pos += 6; ac = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
        for _ in range(ac): pos += 2; al = struct.unpack('>I', data[pos:pos+4])[0]; pos += 4 + al
    mc = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
    all_h_calls = []
    for mi in range(mc):
        m_name_idx = struct.unpack('>H', data[pos+2:pos+4])[0]
        pos += 6
        m_name = resolve_utf8(constants, m_name_idx)
        bytecode = None
        attr_count = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
        for _ in range(attr_count):
            attr_name_idx = struct.unpack('>H', data[pos:pos+2])[0]
            attr_len = struct.unpack('>I', data[pos+2:pos+6])[0]
            attr_name = resolve_utf8(constants, attr_name_idx)
            if attr_name == 'Code':
                code_length = struct.unpack('>I', data[pos+10:pos+14])[0]
                bytecode = data[pos+14:pos+14+code_length]
            pos += 6 + attr_len
        if bytecode:
            h_calls = find_h_calls_in_bytecode(bytecode, constants)
            for hc in h_calls:
                hc['caller_class'] = class_name
                hc['caller_method'] = m_name
                all_h_calls.append(hc)
    return {'class_name': class_name, 'h_calls': all_h_calls} if all_h_calls else None

def main():
    base_dir = sys.argv[1] if len(sys.argv) > 1 else os.path.expanduser('~/github/vibe-coding-labs/iflycode-RE/extracted/jar-contents/com/aicode')
    output_file = sys.argv[2] if len(sys.argv) > 2 else None

    class_files = []
    for root, dirs, files in os.walk(base_dir):
        for fn in files:
            if fn.endswith('.class'): class_files.append(os.path.join(root, fn))

    print(f"Scanning {len(class_files)} .class files...")

    all_results = []
    total_h = 0; quality_counts = {'high': 0, 'medium': 0, 'low': 0, 'garbage': 0}; no_key = 0

    for fp in class_files:
        result = analyze_class(fp)
        if result:
            cn = result['class_name']
            for hc in result['h_calls']:
                total_h += 1
                obf = hc.get('obfuscated')
                target = hc.get('target_class', '')
                v_seq = V_MAP.get(target)
                if obf and v_seq:
                    decoded = xor_decode(obf, v_seq)
                    hc['decoded'] = decoded
                    hc['xor_key_class'] = target
                    if decoded:
                        qlevel, qratio = classify_quality(decoded)
                        hc['quality'] = qratio
                        hc['quality_level'] = qlevel
                        quality_counts[qlevel] += 1
                    else: hc['quality'] = 0; hc['quality_level'] = 'garbage'; quality_counts['garbage'] += 1
                elif obf and not v_seq:
                    no_key += 1
                    hc['decoded'] = None; hc['quality'] = 0; hc['quality_level'] = 'no_key'
                else:
                    quality_counts['garbage'] += 1
                    hc['decoded'] = None; hc['quality'] = 0; hc['quality_level'] = 'garbage'
            all_results.append(result)

    all_results.sort(key=lambda x: -len(x['h_calls']))

    print(f"\n{'='*60}")
    print(f"H() Deobfuscation Summary")
    print(f"{'='*60}")
    print(f"Algorithm: output[i] = input[i] XOR v[(len-i-1)%106+1]")
    print(f"Classes with H() calls: {len(all_results)}")
    print(f"Total H() calls: {total_h}")
    print(f"Quality: high={quality_counts['high']}, medium={quality_counts['medium']}, low={quality_counts['low']}, garbage={quality_counts['garbage']}")
    print(f"No v[] key available: {no_key}")
    usable = quality_counts['high'] + quality_counts['medium']
    if total_h > 0:
        print(f"Usable decode rate (high+medium): {usable/total_h*100:.1f}%")

    # Show decoded strings
    print(f"\n{'='*60}")
    print(f"Decoded Strings by Class (top 30)")
    print(f"{'='*60}")

    all_decoded = []
    for result in all_results:
        cn = result['class_name']
        calls = result['h_calls']
        good_calls = [c for c in calls if c.get('quality_level') in ('high', 'medium')]
        if not good_calls: continue
        print(f"\n  {cn} ({len(good_calls)}/{len(calls)} decoded)")
        for call in good_calls[:20]:
            decoded = call.get('decoded', '')
            all_decoded.append({'class': cn, 'method': call['caller_method'], 'decoded': decoded, 'target': call.get('target_class', ''), 'quality': call.get('quality_level', '')})
            print(f"    {call['caller_method']}(): {repr(decoded[:100])}")
        if len(good_calls) > 20:
            print(f"    ... and {len(good_calls)-20} more")
        # Also include remaining good_calls beyond the 20 printed
        for call in good_calls[20:]:
            decoded = call.get('decoded', '')
            all_decoded.append({'class': cn, 'method': call['caller_method'], 'decoded': decoded, 'target': call.get('target_class', ''), 'quality': call.get('quality_level', '')})

    # Also include low/garbage/no_key entries for completeness
    all_entries = []
    for result in all_results:
        cn = result['class_name']
        for hc in result['h_calls']:
            decoded = hc.get('decoded', '')
            all_entries.append({
                'class': cn,
                'method': hc.get('caller_method', ''),
                'decoded': decoded,
                'obfuscated': hc.get('obfuscated', ''),
                'target': hc.get('target_class', ''),
                'quality': hc.get('quality_level', '')
            })

    # Category analysis on all decoded entries
    categories = defaultdict(list)
    for d in all_entries:
        decoded = d['decoded']
        if not decoded:
            continue
        if decoded.startswith('http') or decoded.startswith('ws'): categories['URL'].append(decoded)
        elif decoded.startswith('/') or decoded.startswith('\\'): categories['Path'].append(decoded)
        elif decoded.isupper() and len(decoded) > 3 and '_' in decoded: categories['Enum/Constant'].append(decoded)
        elif any(0x4E00 <= ord(c) <= 0x9FFF for c in decoded): categories['Chinese Text'].append(decoded)
        elif any(c in decoded for c in '={}<>'): categories['Code/Config'].append(decoded)
        elif '.' in decoded and not decoded.startswith('.'): categories['Class/Package'].append(decoded)
        else: categories['Other'].append(decoded)

    print(f"\n{'='*60}")
    print(f"Decoded String Categories")
    print(f"{'='*60}")
    for cat, items in sorted(categories.items(), key=lambda x: -len(x[1])):
        unique = sorted(set(items))
        print(f"\n  {cat} ({len(unique)} unique)")
        for item in unique[:30]:
            print(f"    {repr(item[:100])}")
        if len(unique) > 30:
            print(f"    ... and {len(unique)-30} more")

    if output_file:
        with open(output_file, 'w', encoding='utf-8') as f:
            json.dump({
                'algorithm': 'output[i] = input[i] XOR v[(len-i-1) % 106 + 1]',
                'summary': {'total_h_calls': total_h, 'quality': quality_counts, 'no_key': no_key, 'classes': len(all_results)},
                'decoded_strings': all_entries,
                'categories': {k: list(set(v)) for k, v in categories.items()}
            }, f, ensure_ascii=False, indent=2)
        print(f"\nResults saved to {output_file}")

if __name__ == '__main__':
    main()