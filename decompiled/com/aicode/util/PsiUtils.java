package com.aicode.util;

import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.language.AICodeLanguageInfo;
import com.intellij.lang.jvm.JvmModifier;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiInvalidElementAccessException;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiJavaToken;
import com.intellij.psi.PsiLiteralValue;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiPlainText;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.TokenType;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/* compiled from: la */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/PsiUtils.class */
public class PsiUtils {
    public static final /* synthetic */ String IDENTIFIER = FileExtensionLanguageDetails.H("|cQH]RJWVS");
    public static final /* synthetic */ String DEFAULT_CLASS_NAME = AICodeLanguageInfo.H("6!\u0018,12\u000egV");

    /* renamed from: try, reason: not valid java name */
    private static final /* synthetic */ Logger f716try = Logger.getInstance(PsiUtils.class);

    /* renamed from: float, reason: not valid java name */
    private static final /* synthetic */ List<String> f717float = Arrays.asList(FileExtensionLanguageDetails.H("."), AICodeLanguageInfo.H("\u0006"), FileExtensionLanguageDetails.H("<1#"), AICodeLanguageInfo.H("H3\u0002"));

    /* renamed from: enum, reason: not valid java name */
    private static final /* synthetic */ TokenSet f719enum = TokenSet.create(new IElementType[]{TokenType.BAD_CHARACTER, TokenType.WHITE_SPACE, TokenType.NEW_LINE_INDENT, TokenType.ERROR_ELEMENT});

    /* renamed from: byte, reason: not valid java name */
    private static final /* synthetic */ String[] f718byte = {FileExtensionLanguageDetails.H("?n~'rmejqh\u007fo9'8p)|ons`w)4DGa/CzCFRoe~Xgcd"), AICodeLanguageInfo.H("\u0014kXT!<\u001c!��\u001e'0\u001cm\u0002?\u00031\u0007x\t\u0019\u00012\u001f,\u001c\u0002\u001c/\u0017\n\u0016d@"), FileExtensionLanguageDetails.H("oq#rWMzel5rHRoe~Xgcd")};

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m432enum(int a) {
        String H = AICodeLanguageInfo.H("\u0005\u00078\u001b*\u0013'\fe\u0012(\u0004$u\u0005\u00157<<\u0014)T>\u001e=\u001fiP+\u000b,OdW5P$Z\u001ck|\u001bmW\fn3\u001a0\u0006f\u00196\u001co\u001c!U1\u001b+\u001a");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            default:
                objArr[0] = FileExtensionLanguageDetails.H("l\u007fej|s");
                break;
            case 7:
                do {
                } while (0 != 0);
                objArr[0] = AICodeLanguageInfo.H(")\t=\u001f)\u0010+\u000b5\u0005");
                break;
        }
        objArr[1] = FileExtensionLanguageDetails.H("wid4mw-3TG<t}rY\bDu`Nxw\u007fr");
        switch (a) {
            case 0:
            default:
                objArr[2] = AICodeLanguageInfo.H("\u001b\f��1\u001b\n\u001c\u0005\u001b8\u001b<;(\u00102\u000b)\u0002");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = FileExtensionLanguageDetails.H("YQPndvPI`Ce~a{}u");
                break;
            case 2:
                objArr[2] = AICodeLanguageInfo.H("\u00060;+\u00076\u001a;;(\u00102\u000b)\u0002");
                break;
            case 3:
            case 4:
                objArr[2] = FileExtensionLanguageDetails.H(")9DnzolOP_`G}Xmlvu");
                break;
            case 5:
                objArr[2] = AICodeLanguageInfo.H("\u0010<\u001c\f\u0011 \u0010\u0016��!\u0019");
                break;
            case 6:
                objArr[2] = FileExtensionLanguageDetails.H("|PSRoe~Epun");
                break;
            case 7:
                objArr[2] = AICodeLanguageInfo.H("\u0019!,\u0002\"\u0006\u000b\u0012-�� \u001a\u0014\u0014-\u000f*\u0005");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String getPackageName(PsiClass a) {
        PsiJavaFile containingFile = a.getContainingFile();
        if (!(containingFile instanceof PsiJavaFile)) {
            return "";
        }
        return containingFile.getPackageName();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ PsiElement skipEmptyAndJavaTokenForward(PsiElement a) {
        if (a != null) {
            PsiJavaToken nextSibling = a.getNextSibling();
            PsiJavaToken psiJavaToken = nextSibling;
            while (nextSibling != null) {
                boolean z = (psiJavaToken instanceof PsiJavaToken) && !AICodeLanguageInfo.H("\u0011-\u0018\"\u001f3\u001e \u000b-").equals(psiJavaToken.getTokenType().toString());
                PsiJavaToken psiJavaToken2 = psiJavaToken;
                boolean isEmpty = psiJavaToken2.getText().isEmpty();
                boolean z2 = psiJavaToken2 instanceof PsiWhiteSpace;
                boolean z3 = psiJavaToken instanceof PsiComment;
                if (isEmpty || z2 || z3 || z) {
                    nextSibling = psiJavaToken.getNextSibling();
                    psiJavaToken = nextSibling;
                } else {
                    return psiJavaToken;
                }
            }
            return null;
        }
        return null;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isPrivateModifier(PsiModifierListOwner a) {
        if (a != null && a.getModifierList() != null) {
            return a.getModifierList().hasModifierProperty(FileExtensionLanguageDetails.H("v{rz\u007fgd").trim());
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ Predicate<PsiModifierListOwner> generateVisibilityPredicator(PsiClass a, PsiClass a2) {
        Predicate<PsiModifierListOwner> predicate = a3 -> {
            if (isPublicModifier(a3)) {
                return true;
            }
            return false;
        };
        if (a != null && a2 != null) {
            String qualifiedName = a.getQualifiedName();
            String packageName = getPackageName(a);
            String qualifiedName2 = a2.getQualifiedName();
            String packageName2 = getPackageName(a2);
            return new J(org.apache.commons.lang3.StringUtils.isEmpty(packageName) || org.apache.commons.lang3.StringUtils.isEmpty(packageName2) || org.apache.commons.lang3.StringUtils.isEmpty(qualifiedName) || org.apache.commons.lang3.StringUtils.isEmpty(qualifiedName2), packageName2.equals(packageName), packageName2.equals(qualifiedName));
        }
        return predicate;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isProtectedModifier(PsiModifierListOwner a) {
        if (a == null || a.getModifierList() == null) {
            return false;
        }
        return a.getModifierList().hasModifierProperty(FileExtensionLanguageDetails.H("Wfi}~ojve").trim());
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String parsePsiType(PsiType a) {
        return a == null ? "" : a.getPresentableText();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isPublicModifier(PsiModifierListOwner a) {
        if (a != null && a.getModifierList() != null) {
            return a.getModifierList().hasModifierProperty(AICodeLanguageInfo.H("3\u0007-\u00121\n").trim());
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ PsiMethod[] getMethodsFromPsiClass(PsiClass a, boolean z, String str, Predicate<PsiModifierListOwner> predicate) {
        String str2 = str;
        if (a != null) {
            if (str2 == null) {
                str2 = "";
            }
            String str3 = str2;
            PsiMethod[] psiMethodArr = new PsiMethod[0];
            try {
                psiMethodArr = (PsiMethod[]) ((List) Arrays.stream(a.getAllMethods()).filter(a2 -> {
                    if (((PsiMethod) a2).getName().toLowerCase().startsWith(str3)) {
                        return !z || ((PsiMethod) a2).hasModifier(JvmModifier.STATIC);
                    }
                    return false;
                }).collect(Collectors.toList())).toArray(new PsiMethod[0]);
                return psiMethodArr;
            } catch (PsiInvalidElementAccessException e) {
                f716try.warn("Get all methods encountered PsiInvalidElementAccessException" + e.getMessage());
                return psiMethodArr;
            }
        }
        return new PsiMethod[0];
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ PsiField[] getFieldsFromPsiClass(PsiClass a, boolean z, String str, Predicate<PsiModifierListOwner> predicate) {
        String str2 = str;
        if (a != null) {
            if (str2 == null) {
                str2 = "";
            }
            String str3 = str2;
            return (PsiField[]) ((List) Arrays.stream(a.getAllFields()).filter(a2 -> {
                if (((PsiField) a2).getName().toLowerCase().startsWith(str3)) {
                    return !z || ((PsiField) a2).hasModifier(JvmModifier.STATIC);
                }
                return false;
            }).collect(Collectors.toList())).toArray(new PsiField[0]);
        }
        return new PsiField[0];
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isNotInClassElement(PsiElement element, @NotNull Editor editor) {
        if (editor == null) {
            m432enum(0);
        }
        VirtualFile file = FileDocumentManager.getInstance().getFile(editor.getDocument());
        if (file != null && !file.getPath().endsWith(FileExtensionLanguageDetails.H("5f\u007fe`"))) {
            return false;
        }
        if (!instanceOf(element, AICodeLanguageInfo.H("=��\u000e|-\u001b\u001d=*\u001b\"\u0010w\u0018*\u0001A\u000e5\u001e\u0006\u001a\"\u0018;\u0017\"\u001c;\u001d")) || !(element.getParent() instanceof PsiErrorElement) || !element.getParent().getErrorDescription().contains(FileExtensionLanguageDetails.H("1f{//f 4i{;+w (UPu`j~\u0012\u0007q~y~ojve"))) {
            if (!(element instanceof PsiWhiteSpace) || !instanceOf(element.getParent(), AICodeLanguageInfo.H("��=)[��62\u0012'\u00160\u0002w\u0018\u001c7h'<\u0017\r\u00179\u001f\u0002\u001c2\n"))) {
                return false;
            }
            String text = editor.getDocument().getText();
            int offset = editor.getCaretModel().getOffset();
            return text.lastIndexOf(FileExtensionLanguageDetails.H("x`\u007f`r"), offset) <= -1 || text.indexOf(AICodeLanguageInfo.H("\u0012"), offset) <= -1;
        }
        return true;
    }

    /* compiled from: la */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/PsiUtils$J.class */
    class J implements Predicate<PsiModifierListOwner> {

        /* renamed from: float, reason: not valid java name */
        public final /* synthetic */ boolean f720float;

        /* renamed from: byte, reason: not valid java name */
        public final /* synthetic */ boolean f721byte;

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ boolean f722enum;

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        @Override // java.util.function.Predicate
        /* renamed from: default, reason: not valid java name and merged with bridge method [inline-methods] */
        public /* synthetic */ boolean test(PsiModifierListOwner a) {
            if (this.f721byte || !this.f722enum) {
                if (!PsiUtils.isPublicModifier(a)) {
                    return false;
                }
                return true;
            }
            if (this.f722enum && !this.f720float) {
                if (!PsiUtils.isPrivateModifier(a)) {
                    return true;
                }
                return false;
            }
            return true;
        }

        public /* synthetic */ J(boolean z, boolean z2, boolean z3) {
            this.f721byte = z;
            this.f722enum = z2;
            this.f720float = z3;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ CodeInfoDto getCodeInfo(@NotNull Editor editor, PsiElement element) {
        if (editor == null) {
            m432enum(5);
        }
        Document document = editor.getDocument();
        int startOffset = element.getTextRange().getStartOffset();
        int endOffset = element.getTextRange().getEndOffset();
        int lineNumber = document.getLineNumber(startOffset);
        int lineNumber2 = document.getLineNumber(endOffset);
        String text = document.getText(new TextRange(document.getLineStartOffset(lineNumber2), document.getLineEndOffset(lineNumber2)));
        CodeInfoDto codeInfoDto = new CodeInfoDto();
        if (!g(editor, document, codeInfoDto, element)) {
            int lineStartOffset = document.getLineStartOffset(lineNumber);
            String text2 = document.getText(new TextRange(lineStartOffset, startOffset));
            int i = org.apache.commons.lang3.StringUtils.isBlank(text2) ? 0 : startOffset - lineStartOffset;
            ArrayList arrayList = new ArrayList();
            CodeInfoDto.RangeDTO rangeDTO = new CodeInfoDto.RangeDTO(Integer.valueOf(lineNumber), Integer.valueOf(i));
            CodeInfoDto.RangeDTO rangeDTO2 = new CodeInfoDto.RangeDTO(Integer.valueOf(lineNumber2), Integer.valueOf(text.length()));
            arrayList.add(rangeDTO);
            arrayList.add(rangeDTO2);
            codeInfoDto.setRange(arrayList);
            codeInfoDto.setContent(org.apache.commons.lang3.StringUtils.isBlank(text2) ? text2 + element.getText() : element.getText());
            codeInfoDto.setAllContent(editor.getDocument().getText());
            return codeInfoDto;
        }
        return null;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String getPsiClassName(PsiFile a, SelectionModel a2) {
        if (a == null) {
            return FileExtensionLanguageDetails.H("cqkfX`\u007f`r");
        }
        try {
            Class.forName(AICodeLanguageInfo.H("\f\u0015&w\u0001-\u0006\f4*\u001e!T)\u001b0F4&\"9+\u0017<\r"));
            return JavaPsiUtils.getPsiClassName(a, a2);
        } catch (ClassNotFoundException unused) {
            return FileExtensionLanguageDetails.H("cqkfX`\u007f`r");
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isInvalidCodeElement(PsiElement a) {
        try {
            Class.forName(AICodeLanguageInfo.H("\u0019)\u001am\u001b \u000b\f4)\u001d$Q?\r\u0006p\u0016\u0004&4&��.*+\u001e;\u0001"));
            return JavaPsiUtils.isInvalidCodeElement(a);
        } catch (ClassNotFoundException unused) {
            return f719enum.contains(a.getNode().getElementType()) || (a instanceof PsiComment) || (a instanceof PsiWhiteSpace) || (a instanceof PsiPlainText) || (a instanceof PsiLiteralValue) || (a instanceof PsiErrorElement);
        }
    }

    public static /* synthetic */ String getLineTextAtCaret(@NotNull Editor editor) {
        if (editor == null) {
            m432enum(3);
        }
        int lineNumber = editor.getDocument().getLineNumber(editor.getCaretModel().getOffset());
        return editor.getDocument().getText(new TextRange(editor.getDocument().getLineStartOffset(lineNumber), editor.getDocument().getLineEndOffset(lineNumber)));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isPythonIdentifier(String a) {
        try {
            Object invoke = ReflectUtil.classForName(FileExtensionLanguageDetails.H("-3x)~c}y~\u007f'2C\fcx}sZI:VpUmsvr")).getDeclaredMethod(AICodeLanguageInfo.H("7\u001c\n\u0016)\u00137\u001b)\u0017=\u001b"), String.class).invoke(null, a);
            if (invoke != null) {
                return ((Boolean) invoke).booleanValue();
            }
        } catch (Exception e) {
            f716try.warn(FileExtensionLanguageDetails.H("{e\u007fi7:35d|cjp,w=\fIV{ngRQBzr`}e{a/"), e);
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ Object getField(String a, String a2) {
        try {
            return Class.forName(a).getField(a2).get(null);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* renamed from: transient, reason: not valid java name */
    private static /* synthetic */ boolean m422transient(char c) {
        return Character.isJavaIdentifierPart(c) || c == '_' || c == '$';
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ PsiElement findNextAtOffset(PsiFile a, int a2, Class... a3) {
        PsiElement findElementAt = a.findElementAt(a2);
        PsiElement psiElement = findElementAt;
        if (findElementAt == null) {
            return null;
        }
        Document document = PsiDocumentManager.getInstance(a.getProject()).getDocument(a);
        int i = 0;
        if (document != null) {
            i = document.getLineEndOffset(document.getLineNumber(a2));
        }
        while (a2 < i && instanceOf(psiElement, a3)) {
            a2++;
            psiElement = a.findElementAt(a2);
        }
        if (instanceOf(psiElement, a3)) {
            return null;
        }
        return psiElement;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ PsiElement getCaratElement(Editor a) {
        if (a.getProject() == null) {
            return null;
        }
        int offset = a.getCaretModel().getOffset();
        PsiFile psiFile = PsiDocumentManager.getInstance(a.getProject()).getPsiFile(a.getDocument());
        PsiElement psiElement = null;
        if (psiFile != null && offset > 0) {
            psiElement = findElementAtOffset(psiFile, offset);
        }
        return psiElement;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String getPsiMethodContent(Project a, PsiFile a2, SelectionModel a3) {
        String selectedText = a3.getSelectedText();
        if (a2 == null) {
            return selectedText;
        }
        try {
            Class.forName(AICodeLanguageInfo.H("\u00049\"P7\u00016\u0016)\u0018'\u0015G(6\u001dj%-\u0006\u0002\u001b0\u001d1\u000b"));
            return JavaPsiUtils.getPsiMethodContent(a, a2, a3);
        } catch (ClassNotFoundException unused) {
            return selectedText;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String formatMethodId(PsiClass a, String a2, PsiParameter[] a3) {
        return (a == null ? null : a.getQualifiedName()) + "#" + a2 + "(" + m428assert(a3) + ")";
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* renamed from: assert, reason: not valid java name */
    private static /* synthetic */ String m428assert(PsiParameter[] a) {
        StringBuilder sb = new StringBuilder();
        if (a != null) {
            int length = a.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                StringBuilder append = sb.append(a[i2].getType().getCanonicalText());
                i2++;
                append.append(FileExtensionLanguageDetails.H("-"));
                i = i2;
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String getPsiDocContent(Project project, PsiFile a, SelectionModel a2) {
        if (a == null) {
            return a2.getSelectedText();
        }
        int selectionStart = a2.getSelectionStart();
        int selectionEnd = a2.getSelectionEnd();
        PsiElement findElementAt = a.findElementAt(selectionStart);
        PsiComment parentOfType = PsiTreeUtil.getParentOfType(a.findElementAt(selectionEnd), PsiComment.class, false);
        PsiComment parentOfType2 = PsiTreeUtil.getParentOfType(findElementAt, PsiComment.class, false);
        if (parentOfType != null) {
            try {
                Class.forName(AICodeLanguageInfo.H("\u0019$4F*\u001c\u001a:)\u0018 \u0012G(6\u001dj%-\u0006\u0002\u001b0\u001d1\u000b"));
                return JavaPsiUtils.getFollowingMethodSignatureFromComment(parentOfType);
            } catch (ClassNotFoundException unused) {
                return parentOfType.getText();
            }
        }
        if (parentOfType2 != null) {
            try {
                Class.forName(FileExtensionLanguageDetails.H("vhy(`ux{\"0YH=qzr\u001bwgoD~xv|e"));
                return JavaPsiUtils.getFollowingMethodSignatureFromComment(parentOfType2);
            } catch (ClassNotFoundException unused2) {
                return parentOfType2.getText();
            }
        }
        return a2.getSelectedText();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* renamed from: protected, reason: not valid java name */
    private static /* synthetic */ String m425protected(@NlsSafe @NotNull PsiType[] parameters) {
        if (parameters == null) {
            m432enum(7);
        }
        StringBuilder sb = new StringBuilder();
        if (parameters != null) {
            int length = parameters.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                StringBuilder append = sb.append(parameters[i2].getCanonicalText());
                i2++;
                append.append(AICodeLanguageInfo.H("S"));
                i = i2;
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isCommentElement(PsiElement element, @NotNull Editor editor) {
        TextRange textRange;
        if (editor == null) {
            m432enum(1);
        }
        if ((element instanceof PsiComment) || instanceOf(element, AICodeLanguageInfo.H(" \u001d4F\u000e7,\u0010\"X7\u0005&P-\u0018.\u0003A\u0019)4 \u0013*\u0013!\n\r\u0018.\u0003"))) {
            return true;
        }
        if ((element.getParent() == null || !instanceOf(element.getParent(), FileExtensionLanguageDetails.H("lri8ly:9yk}l'k\u007fw`6QTrefx\u001bwgoMtoJrf"), AICodeLanguageInfo.H(";*\u0019i\u001f)\u0002*\u0012(\u001c4A\u001f-/Y\u001f\r.5 \u0013)\u00100\u001b"))) && !f717float.stream().anyMatch(a -> {
            return element.getText().contains(a);
        })) {
            if (element.getPrevSibling() == null || !(element.getPrevSibling() instanceof PsiComment) || (textRange = element.getPrevSibling().getTextRange()) == null || (!textRange.contains(editor.getCaretModel().getOffset()) && editor.getDocument().getLineNumber(textRange.getEndOffset()) != editor.getDocument().getLineNumber(editor.getCaretModel().getOffset()))) {
                if (element.getPrevSibling() == null || !f717float.stream().anyMatch(a2 -> {
                    return element.getPrevSibling().getText().contains(a2);
                }) || instanceOf(element.getPrevSibling(), FileExtensionLanguageDetails.H("vhy(`ux{\"0YH=qzr\u001bwgoD~xv|e")) || editor.getDocument().getLineNumber(element.getPrevSibling().getTextOffset()) != editor.getDocument().getLineNumber(editor.getCaretModel().getOffset())) {
                    return false;
                }
                return true;
            }
            return true;
        }
        return true;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static /* synthetic */ boolean g(@NotNull Editor editor, Document document, CodeInfoDto codeInfoDto, PsiElement element) {
        if (editor == null) {
            m432enum(6);
        }
        try {
            PsiFile psiFile = PsiDocumentManager.getInstance((Project) Objects.requireNonNull(editor.getProject())).getPsiFile(document);
            if (psiFile != null) {
                String lowerCase = psiFile.getLanguage().getID().toLowerCase();
                VirtualFile file = FileDocumentManager.getInstance().getFile(editor.getDocument());
                if (file != null) {
                    String path = file.getPath();
                    if (org.apache.commons.lang3.StringUtils.isBlank(path)) {
                        return true;
                    }
                    codeInfoDto.setFileName(Paths.get(path, new String[0]).getFileName().toString());
                    codeInfoDto.setPath(path);
                    codeInfoDto.setLanguage(lowerCase);
                    codeInfoDto.setContent(element.getText());
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ Boolean containsCode(Project project, PsiFile a, SelectionModel a2) {
        PsiElement findElementAt;
        if (a != null) {
            int selectionStart = a2.getSelectionStart();
            int selectionEnd = a2.getSelectionEnd();
            int i = selectionStart;
            while (true) {
                int i2 = i;
                if (i < selectionEnd && (findElementAt = a.findElementAt(i2)) != null) {
                    if (findElementAt.getText().length() <= 1 || isInvalidCodeElement(findElementAt)) {
                        i = findElementAt.getTextRange().getEndOffset();
                    } else {
                        f716try.info("Valid element: " + findElementAt.getText() + ", type=" + findElementAt.getNode().getElementType());
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String getLanguageByIDE() {
        String versionName = ApplicationInfo.getInstance().getVersionName();
        if (!versionName.toLowerCase().contains(AICodeLanguageInfo.H("\u0007;\n?"))) {
            if (!versionName.toLowerCase().contains(AICodeLanguageInfo.H("\u0012\u001a1\u0006>\u001d3"))) {
                if (versionName.toLowerCase().contains(AICodeLanguageInfo.H("1\u00026��0"))) {
                    return FileExtensionLanguageDetails.H("b");
                }
                if (versionName.toLowerCase().contains(AICodeLanguageInfo.H("$\u0007\u0001!\u001a0\u001d3"))) {
                    return FileExtensionLanguageDetails.H("_Fbgzx~wcu");
                }
                if (versionName.toLowerCase().contains(AICodeLanguageInfo.H("\u0004=\u0002>\u0001:"))) {
                    return FileExtensionLanguageDetails.H("tn");
                }
                return null;
            }
            return FileExtensionLanguageDetails.H("ybxv|o");
        }
        return FileExtensionLanguageDetails.H("f\u007fe`");
    }

    public static /* synthetic */ PsiElement findElementAtOffset(PsiFile a, int a2) {
        PsiElement findPrevAtOffset = findPrevAtOffset(a, a2, new Class[0]);
        PsiElement psiElement = findPrevAtOffset;
        if (findPrevAtOffset == null) {
            psiElement = findNextAtOffset(a, a2, new Class[0]);
        }
        return psiElement;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ PsiElement findPrevAtOffset(PsiFile a, int a2, Class... a3) {
        PsiElement findElementAt;
        if (a2 >= 0) {
            int i = 0;
            Document document = PsiDocumentManager.getInstance(a.getProject()).getDocument(a);
            if (document != null) {
                i = document.getLineStartOffset(document.getLineNumber(a2));
            }
            while (true) {
                a2--;
                findElementAt = a.findElementAt(a2);
                if (a2 < i || (findElementAt != null && !instanceOf(findElementAt, a3))) {
                    break;
                }
            }
            if (instanceOf(findElementAt, a3)) {
                return null;
            }
            return findElementAt;
        }
        return null;
    }

    public static /* synthetic */ String getLineTextAtCaret(@NotNull Editor editor, int i) {
        int i2 = i;
        if (editor == null) {
            m432enum(4);
        }
        if (i2 > editor.getDocument().getTextLength()) {
            i2 = editor.getDocument().getTextLength();
        }
        int lineNumber = editor.getDocument().getLineNumber(i2);
        return editor.getDocument().getText(new TextRange(editor.getDocument().getLineStartOffset(lineNumber), editor.getDocument().getLineEndOffset(lineNumber)));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ FileType getFileType() {
        FileType fileType = null;
        LanguageFileType languageFileType = FileTypes.PLAIN_TEXT;
        String[] strArr = f718byte;
        int length = strArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            try {
                fileType = (FileType) ReflectUtil.getStaticField(ReflectUtil.classForName(strArr[i2]), FileExtensionLanguageDetails.H("]HZOMPPD"));
            } catch (Exception unused) {
            }
            i2++;
            i = i2;
        }
        return fileType;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ int getLineCount(int a, PsiElement a2) {
        TextRange textRange;
        Document document = PsiDocumentManager.getInstance(a2.getProject()).getDocument(a2.getContainingFile());
        if (document != null && (textRange = a2.getTextRange()) != null) {
            return (document.getLineNumber(textRange.getEndOffset()) - document.getLineNumber(a)) + 1;
        }
        return 0;
    }

    public static /* synthetic */ PsiElement findNonWhitespaceAtOffset(PsiFile a, int a2) {
        PsiElement findNextAtOffset = findNextAtOffset(a, a2, PsiWhiteSpace.class);
        PsiElement psiElement = findNextAtOffset;
        if (findNextAtOffset == null) {
            psiElement = findPrevAtOffset(a, a2 - 1, PsiWhiteSpace.class);
        }
        return psiElement;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isJavaMethodNewLine(Editor a, PsiElement a2, int a3, int a4) {
        if (a2.getPrevSibling() != null && instanceOf(a2.getPrevSibling(), FileExtensionLanguageDetails.H("msh9j\u007f{xas'6/czr-xbmh8vx;.vb:r{~i0$=FC=Llo]HpCe~a{}u"), AICodeLanguageInfo.H("%\u00184F*\u001c-\r\f=\"\u0010r\u001d8\u0013I?\"\u000e2A1\u001c0\u0006-\u001aJ\u0005<\u0017\u0010\t\u0012?,\u0016\r\u0018?\u0012")) && a4 >= a2.getPrevSibling().getTextRange().getEndOffset() && a4 > 0 && a3 > a4 && FileExtensionLanguageDetails.H("|").equals(a.getDocument().getText(new TextRange(a4 - 1, a3)).trim())) {
            f716try.info(AICodeLanguageInfo.H("\u00141\r \u0019y\u0005\u0005%#\u00158M.\u0014\u0003v \u0018~\u0001'\u0004)\u001d \u001aHu!\u0011)L\u0012%*\u0015#\u0010=P"));
            return true;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean checkCaretAround(Editor a) {
        int offset = a.getCaretModel().getOffset();
        LogicalPosition logicalPosition = a.getCaretModel().getLogicalPosition();
        int lineStartOffset = a.getDocument().getLineStartOffset(logicalPosition.line);
        int i = offset - lineStartOffset;
        String text = a.getDocument().getText(new TextRange(lineStartOffset, a.getDocument().getLineEndOffset(logicalPosition.line)));
        if (i > 0 && i < text.length()) {
            char charAt = text.charAt(i);
            char charAt2 = text.charAt(i - 1);
            if (!m422transient(charAt) || (!m422transient(charAt2) && charAt2 != '(')) {
                if (i > 1) {
                    char charAt3 = text.charAt(i - 2);
                    if (m422transient(charAt) && (charAt2 == '=' || (charAt2 == ' ' && charAt3 == '='))) {
                        f716try.info(AICodeLanguageInfo.H("\u001a!\b(\u0014(\u0014I($\t1\u001d&\u00110O\n<,\f7TbN<\r6\u0007"));
                        return false;
                    }
                }
            } else {
                f716try.info(FileExtensionLanguageDetails.H("\u007fka/0|c4vfhej'3^\u0002zo)lZUp&drhz\u007fd"));
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean instanceOf(Object a, Class... a2) {
        if (a == null || a2 == null) {
            return false;
        }
        int length = a2.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            if (a2[i2].isInstance(a)) {
                return true;
            }
            i2++;
            i = i2;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isImportElement(PsiElement element, @NotNull Editor editor) {
        if (editor == null) {
            m432enum(2);
        }
        if (element != null && instanceOf(element.getParent(), AICodeLanguageInfo.H(" \u001d\u0012`7\u00017\u0017*\u001b0\u0002m\u0002*\u0001M;*\u00066E6\u001b-\u001b=\nA\u000e5\u001e\u0006\u00137\u0019=\n\b\u001c-\u001b"), FileExtensionLanguageDetails.H("oqh9dqqrbp}l-a|t#v#,m=ztvclx*Fv~\u0004=cfWim~^{(9BG}bl^YBycgoEscm"), AICodeLanguageInfo.H(" \u001d\u0012`9��*\u0016#Y)\u001b*\\0\u0005\u0013>i15\"(\u00047\u001b*+\n=*\u0016=\u001f3\u001f \u0010\r\u0018.\u0003"))) {
            return true;
        }
        String lineTextAtCaret = getLineTextAtCaret(editor);
        if (!lineTextAtCaret.startsWith(FileExtensionLanguageDetails.H("odkclg!")) && !lineTextAtCaret.startsWith(AICodeLanguageInfo.H("\u00186\u001a3O")) && !lineTextAtCaret.startsWith(FileExtensionLanguageDetails.H("|hept!"))) {
            return false;
        }
        return true;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* renamed from: throws, reason: not valid java name */
    private static /* synthetic */ String m423throws(PsiComment a) {
        if (a != null) {
            return ((PsiComment) Objects.requireNonNullElse(PsiTreeUtil.getParentOfType(a, PsiDocComment.class, true), a)).getText();
        }
        return "";
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String formatMethodId(PsiClass a, String a2, PsiType[] a3) {
        return (a == null ? null : a.getQualifiedName()) + "#" + a2 + "(" + m425protected(a3) + ")";
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public static /* synthetic */ boolean instanceOf(Object a, String... a2) {
        if (a != null && a2 != null) {
            String name = a.getClass().getName();
            int length = a2.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                String str = a2[i2];
                try {
                } catch (ClassNotFoundException unused) {
                } catch (Exception unused2) {
                    f716try.debug("fail to instanceOf Class:" + str);
                }
                if (!str.equals(name) && !ReflectUtil.classForName(str).isInstance(a)) {
                    i2++;
                    i = i2;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ PsiClass h(String a, Module a2, Project a3) {
        if (a2 == null) {
            return JavaPsiFacade.getInstance(a3).findClass(a, GlobalSearchScope.everythingScope(a3));
        }
        return JavaPsiFacade.getInstance(a3).findClass(a, GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(a2));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String getLanguageByCurrentFile(Editor a) {
        String path;
        int lastIndexOf;
        VirtualFile file = FileDocumentManager.getInstance().getFile(a.getDocument());
        if (file == null || (lastIndexOf = (path = file.getPath()).lastIndexOf(AICodeLanguageInfo.H("P"))) <= 0) {
            return null;
        }
        return path.substring(lastIndexOf + 1);
    }
}
