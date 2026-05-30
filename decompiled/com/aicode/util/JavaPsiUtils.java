package com.aicode.util;

import cn.hutool.core.collection.CollectionUtil;
import com.aicode.template.TypeDictionary;
import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.Param;
import com.aicode.template.context.domain.Type;
import com.aicode.template.context.resolved.MethodCallArg;
import com.aicode.template.context.resolved.ResolvedBranch;
import com.aicode.template.context.resolved.ResolvedMethodCall;
import com.aicode.template.context.resolved.ResolvedReference;
import com.intellij.ide.hierarchy.HierarchyNodeDescriptor;
import com.intellij.ide.hierarchy.type.SubtypesHierarchyTreeStructure;
import com.intellij.ide.hierarchy.type.TypeHierarchyNodeDescriptor;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiBinaryExpression;
import com.intellij.psi.PsiCallExpression;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiEnumConstant;
import com.intellij.psi.PsiEnumConstantInitializer;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionList;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiIfStatement;
import com.intellij.psi.PsiJavaToken;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiMethodReferenceExpression;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiReturnStatement;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiThrowStatement;
import com.intellij.psi.PsiTryStatement;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiTypeCastExpression;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.PsiWhileStatement;
import com.intellij.psi.TokenType;
import com.intellij.psi.impl.source.tree.ElementType;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/JavaPsiUtils.class */
public class JavaPsiUtils {
    private static final Set<String> INVALID_METHOD_NAMES = new HashSet(Arrays.asList("equals", "hashCode", "toString"));
    private static final TokenSet INVALID_JAVA_TOKENSET = TokenSet.orSet(new TokenSet[]{ElementType.ALL_LITERALS, ElementType.JAVA_COMMENT_OR_WHITESPACE_BIT_SET, JavaDocTokenType.ALL_JAVADOC_TOKENS, TokenSet.create(new IElementType[]{TokenType.WHITE_SPACE, TokenType.BAD_CHARACTER, TokenType.NEW_LINE_INDENT, TokenType.ERROR_ELEMENT})});
    private static final Pattern PATTERN = Pattern.compile("\\s*(\\w*)\\(\\):(-?\\d*), (\\w*\\.java)\\n");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 8:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 8:
            default:
                i2 = 3;
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            default:
                objArr[0] = "element";
                break;
            case 1:
            case 3:
                objArr[0] = "method";
                break;
            case 2:
                objArr[0] = "aClass";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
                objArr[0] = "com/aicode/util/JavaPsiUtils";
                break;
            case 8:
                objArr[0] = "condition";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 8:
            default:
                objArr[1] = "com/aicode/util/JavaPsiUtils";
                break;
            case 4:
                objArr[1] = "resolveJavaEnumValues";
                break;
            case 5:
                objArr[1] = "findResolvedMethodCalls";
                break;
            case 6:
                objArr[1] = "findReferences";
                break;
            case 7:
                objArr[1] = "findMethodReferences";
                break;
            case 9:
                objArr[1] = "getPsiClasses";
                break;
        }
        switch (i) {
            case 0:
            default:
                objArr[2] = "isInvalidJavaMethod";
                break;
            case 1:
                objArr[2] = "isAbstractMethod";
                break;
            case 2:
            case 3:
                objArr[2] = "isDefaultMethod";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
                break;
            case 8:
                objArr[2] = "resolveBranchName";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 8:
            default:
                throw new IllegalArgumentException(format);
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
                throw new IllegalStateException(format);
        }
    }

    public static boolean isInvalidCodeElement(PsiElement curElement) {
        return INVALID_JAVA_TOKENSET.contains(curElement.getNode().getElementType());
    }

    public static String getPsiMethodContent(Project project, PsiFile psiFile, SelectionModel selectionModel) {
        String result = selectionModel.getSelectedText();
        int start = selectionModel.getSelectionStart();
        int end = selectionModel.getSelectionEnd();
        PsiElement startElement = psiFile.findElementAt(start);
        PsiMethod startMethod = PsiTreeUtil.getParentOfType(startElement, PsiMethod.class);
        PsiElement endElement = psiFile.findElementAt(end);
        PsiMethod endMethod = PsiTreeUtil.getParentOfType(endElement, PsiMethod.class);
        if (startMethod == null && endMethod == null) {
            return result;
        }
        if (startMethod != null && endMethod == null) {
            return startMethod.getText();
        }
        if (startMethod == null && endMethod != null) {
            return endMethod.getText();
        }
        Document document = PsiDocumentManager.getInstance(project).getDocument(psiFile);
        if (document != null) {
            result = document.getText(new TextRange(startMethod.getTextRange().getStartOffset(), endMethod.getTextRange().getEndOffset()));
        }
        return result;
    }

    public static String getPsiClassName(PsiFile psiFile, SelectionModel selectionModel) {
        int start = selectionModel.getSelectionStart();
        int end = selectionModel.getSelectionEnd();
        PsiElement startElement = psiFile.findElementAt(start);
        PsiElement endElement = psiFile.findElementAt(end);
        PsiClass startClass = PsiTreeUtil.getParentOfType(startElement, PsiClass.class, false);
        PsiClass endClass = PsiTreeUtil.getParentOfType(endElement, PsiClass.class, false);
        if (startClass != null && startClass.equals(endClass)) {
            return startClass.getName();
        }
        return "DemoClass";
    }

    public static FileType getJavaFileType() {
        return JavaFileType.INSTANCE;
    }

    public static String getFollowingMethodSignatureFromComment(PsiComment psiComment) {
        if (psiComment == null) {
            return "";
        }
        if (psiComment.getParent() instanceof PsiMethod) {
            getMethodSignature(psiComment.getParent());
            return psiComment.getText() + "\n" + psiComment.getText();
        }
        return psiComment.getText();
    }

    private static String getMethodSignature(PsiMethod method) {
        StringBuilder sb = new StringBuilder();
        PsiIdentifier psiIdentifier = method.getNameIdentifier();
        sb.append(method.getModifierList().getText());
        if (method.getReturnTypeElement() != null) {
            sb.append(" ").append(method.getReturnTypeElement().getText());
        }
        if (psiIdentifier != null) {
            sb.append(" ").append(psiIdentifier.getText());
        }
        sb.append("(");
        PsiParameter[] parameters = method.getParameterList().getParameters();
        for (int i = 0; i < parameters.length; i++) {
            PsiParameter parameter = parameters[i];
            if (parameter.getTypeElement() != null) {
                sb.append(parameter.getTypeElement().getText()).append(" ").append(parameter.getName());
                if (i < parameters.length - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static boolean isInvalidJavaMethod(@NotNull PsiElement element) {
        if (element == null) {
            $$$reportNull$$$0(0);
        }
        try {
            if (!PsiUtils.instanceOf(element.getParent(), "com.intellij.psi.PsiClass")) {
                return false;
            }
            PsiMethod method = (PsiMethod) element;
            method.getName();
            if (INVALID_METHOD_NAMES.contains(method.getName()) || method.isConstructor() || isAbstractMethod(method)) {
                return true;
            }
            if (!PsiUtils.instanceOf(method.getParent(), "com.intellij.psi.PsiClass")) {
                return false;
            }
            if (isMethodEmpty(method)) {
                return true;
            }
            PsiClass clazz = method.getParent();
            PsiField[] fields = clazz.getAllFields();
            if (fields.length == 0) {
                return false;
            }
            String methodName = method.getName().toLowerCase(Locale.ROOT);
            for (PsiField field : fields) {
                String fieldName = field.getName().toLowerCase(Locale.ROOT);
                String setMethodName = "set" + fieldName;
                String getMethodName = "get" + fieldName;
                String isMethodName = "is" + fieldName;
                if (setMethodName.equals(methodName) || getMethodName.equals(methodName) || isMethodName.equals(methodName) || methodName.equals(fieldName)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean isMethodEmpty(PsiMethod method) {
        PsiCodeBlock body = method.getBody();
        if (body == null) {
            return true;
        }
        String methodText = body.getText().trim();
        return StringUtils.isBlank(methodText.substring(1, methodText.length() - 1).replaceAll("\\s+", ""));
    }

    public static boolean isAbstractMethod(@NotNull PsiMethod method) {
        if (method == null) {
            $$$reportNull$$$0(1);
        }
        if (method.hasModifierProperty("abstract")) {
            return true;
        }
        PsiClass aClass = method.getContainingClass();
        return (aClass == null || !aClass.isInterface() || isDefaultMethod(aClass, method)) ? false : true;
    }

    public static boolean isDefaultMethod(@NotNull PsiClass aClass, @NotNull PsiMethod method) {
        if (aClass == null) {
            $$$reportNull$$$0(2);
        }
        if (method == null) {
            $$$reportNull$$$0(3);
        }
        return method.hasModifierProperty("default") && PsiUtil.getLanguageLevel(aClass).isAtLeast(LanguageLevel.JDK_1_8);
    }

    public static boolean resolveIfEnum(PsiClass psiClass) {
        return psiClass != null && psiClass.isEnum();
    }

    @NotNull
    public static List<String> resolveJavaEnumValues(PsiClass psiClass) {
        List<String> enumValues = new ArrayList<>();
        if (resolveIfEnum(psiClass)) {
            for (PsiEnumConstant psiEnumConstant : psiClass.getFields()) {
                if (psiEnumConstant instanceof PsiEnumConstant) {
                    PsiEnumConstant enumConstant = psiEnumConstant;
                    PsiEnumConstantInitializer initializingClass = enumConstant.getInitializingClass();
                    if (initializingClass == null) {
                        enumValues.add(enumConstant.getName());
                    }
                }
            }
        }
        if (enumValues == null) {
            $$$reportNull$$$0(4);
        }
        return enumValues;
    }

    public static ResolvedBranch findSpecialElementsInMethod(PsiMethod method) {
        ResolvedBranch resolvedBranch = new ResolvedBranch();
        resolvedBranch.setTextRange(method.getTextRange());
        resolvedBranch.setConditionText("");
        resolvedBranch.setMethodName(method.getName());
        resolvedBranch.setResult(true);
        resolvedBranch.setOut(true);
        PsiCodeBlock methodBody = method.getBody();
        if (methodBody == null) {
            return resolvedBranch;
        }
        Collection<PsiIfStatement> ifStatements = PsiTreeUtil.findChildrenOfType(methodBody, PsiIfStatement.class);
        for (PsiIfStatement ifStatement : ifStatements) {
            findSpecialElementInPsiIfStatement(resolvedBranch, null, ifStatement, 0, new ArrayList());
        }
        Collection<PsiTryStatement> tryStatements = PsiTreeUtil.findChildrenOfType(methodBody, PsiTryStatement.class);
        for (PsiTryStatement tryStatement : tryStatements) {
            findSpecialElementInPsiTryStatement(resolvedBranch, null, tryStatement, 0, new ArrayList());
        }
        Collection<PsiWhileStatement> whileStatements = PsiTreeUtil.findChildrenOfType(methodBody, PsiWhileStatement.class);
        for (PsiWhileStatement whileStatement : whileStatements) {
            ResolvedBranch whileBranch = new ResolvedBranch();
            whileBranch.setBranch(whileStatement);
            whileBranch.setLevel(0);
            if (whileStatement.getCondition() != null) {
                whileBranch.setConditionText(whileStatement.getCondition().getText());
            }
            whileBranch.setTextRange(whileStatement.getTextRange());
            whileBranch.setOut(true);
            whileBranch.setResult(true);
            whileBranch.setParent(resolvedBranch);
            whileBranch.setPrev(null);
            resolvedBranch.getChildrenCases().add(whileBranch);
        }
        return resolvedBranch;
    }

    public static void findSpecialElementInPsiIfStatement(ResolvedBranch parent, ResolvedBranch prev, PsiIfStatement ifStatement, int branchLevel, List<String> elseBranches) {
        if (parent.getChildrenCases().stream().anyMatch(r -> {
            return r.getBranch().equals(ifStatement);
        })) {
            return;
        }
        PsiExpression condition = ifStatement.getCondition();
        ResolvedBranch caseBranch = new ResolvedBranch();
        caseBranch.setConditionText(condition.getText());
        caseBranch.setLevel(Integer.valueOf(branchLevel));
        caseBranch.setTextRange(ifStatement.getTextRange());
        caseBranch.setBranch(ifStatement);
        checkHasReturn(ifStatement, caseBranch);
        caseBranch.setResult(false);
        caseBranch.setElseBranches(elseBranches);
        if (ifStatement.getThenBranch() != null) {
            int endOffset = ifStatement.getThenBranch().getTextRange().getEndOffset();
            caseBranch.setTextRange(new TextRange(caseBranch.getTextRange().getStartOffset(), endOffset));
        }
        caseBranch.setParent(parent);
        if (prev != null) {
            caseBranch.setPrev(prev);
            prev.setNext(caseBranch);
        }
        if (parent.getChildrenCases().stream().anyMatch(r2 -> {
            return StringUtils.equals(r2.getConditionText(), caseBranch.getConditionText());
        })) {
            return;
        }
        parent.getChildrenCases().add(caseBranch);
        PsiIfStatement elseBranch = ifStatement.getElseBranch();
        if (elseBranch != null) {
            if (elseBranch instanceof PsiIfStatement) {
                elseBranches.add(caseBranch.getConditionText());
                findSpecialElementInPsiIfStatement(parent, caseBranch, elseBranch, branchLevel, elseBranches);
            } else {
                ResolvedBranch elseCaseBranch = new ResolvedBranch();
                elseBranches.add(caseBranch.getConditionText());
                elseCaseBranch.setConditionText(jointBranches(elseBranches));
                elseCaseBranch.setTextRange(elseBranch.getTextRange());
                elseCaseBranch.setLevel(Integer.valueOf(branchLevel));
                elseCaseBranch.setVirtual(true);
                elseCaseBranch.setBranch(elseBranch);
                checkHasReturn(elseBranch, elseCaseBranch);
                elseCaseBranch.setResult(true);
                elseCaseBranch.setParent(parent);
                elseCaseBranch.setPrev(caseBranch);
                caseBranch.setNext(elseCaseBranch);
                elseCaseBranch.setElseBranches(elseBranches);
                parent.getChildrenCases().add(elseCaseBranch);
                Collection<PsiIfStatement> elseIfStatements = PsiTreeUtil.findChildrenOfType(elseBranch, PsiIfStatement.class);
                if (CollectionUtil.isNotEmpty(elseIfStatements)) {
                    for (PsiIfStatement statement : elseIfStatements) {
                        findSpecialElementInPsiIfStatement(elseCaseBranch, null, statement, branchLevel + 1, new ArrayList());
                    }
                }
            }
        }
        Collection<PsiIfStatement> ifStatements = PsiTreeUtil.findChildrenOfType(ifStatement.getThenBranch(), PsiIfStatement.class);
        if (CollectionUtil.isNotEmpty(ifStatements)) {
            elseBranches.add(caseBranch.getConditionText());
            for (PsiIfStatement statement2 : ifStatements) {
                findSpecialElementInPsiIfStatement(parent, caseBranch, statement2, branchLevel + 1, elseBranches);
            }
        }
    }

    private static String jointBranches(List<String> elseBranches) {
        StringBuilder stringBuilder = new StringBuilder();
        elseBranches.stream().distinct().forEach(item -> {
            stringBuilder.append(item).append(" == false && ");
        });
        if (stringBuilder.length() > 3) {
            return stringBuilder.substring(0, stringBuilder.length() - 3);
        }
        return "";
    }

    public static void findSpecialElementInPsiTryStatement(ResolvedBranch parent, ResolvedBranch prev, PsiTryStatement tryStatement, int branchLevel, List<String> elseBranches) {
        PsiParameter[] catchBlockParameters = tryStatement.getCatchBlockParameters();
        for (PsiParameter catchBlockParameter : catchBlockParameters) {
            ResolvedBranch caseBranch = new ResolvedBranch();
            caseBranch.setConditionText(catchBlockParameter.getText());
            caseBranch.setLevel(Integer.valueOf(branchLevel));
            caseBranch.setTextRange(catchBlockParameter.getParent().getTextRange());
            caseBranch.setBranch(tryStatement);
            checkHasReturn(tryStatement, caseBranch);
            caseBranch.setResult(false);
            caseBranch.setElseBranches(elseBranches);
            caseBranch.setParent(parent);
            if (prev != null) {
                caseBranch.setPrev(prev);
                prev.setNext(caseBranch);
            }
            if (parent.getChildrenCases().stream().anyMatch(r -> {
                return StringUtils.equals(r.getConditionText(), caseBranch.getConditionText());
            })) {
                return;
            }
            parent.getChildrenCases().add(caseBranch);
        }
    }

    public static void checkHasReturn(PsiStatement psiStatement, ResolvedBranch resolvedBranch) {
        PsiExpression exceptionExpression;
        PsiType exceptionType;
        boolean hasReturn = false;
        PsiReturnStatement returnStatement = PsiTreeUtil.findChildOfType(psiStatement, PsiReturnStatement.class);
        PsiThrowStatement throwStatement = PsiTreeUtil.findChildOfType(psiStatement, PsiThrowStatement.class);
        if (returnStatement != null || throwStatement != null) {
            hasReturn = true;
        }
        resolvedBranch.setOut(Boolean.valueOf(hasReturn));
        if (throwStatement != null && (exceptionExpression = throwStatement.getException()) != null && (exceptionType = exceptionExpression.getType()) != null) {
            String exceptionTypeText = exceptionType.getCanonicalText();
            resolvedBranch.setExceptionCanonicalText(exceptionTypeText);
        }
    }

    @NotNull
    public static List<ResolvedMethodCall> findResolvedMethodCalls(PsiMethod psiMethod) {
        String ownerClassCanonicalType = psiMethod.getContainingClass() == null ? "" : psiMethod.getContainingClass().getQualifiedName();
        List<ResolvedMethodCall> methodCalled = new ArrayList<>();
        findMethodCalls(methodCalled, psiMethod, ownerClassCanonicalType, 4, 0);
        if (methodCalled == null) {
            $$$reportNull$$$0(5);
        }
        return methodCalled;
    }

    @NotNull
    public static void findMethodCalls(List<ResolvedMethodCall> methodCalled, PsiMethod psiMethod, String ownerClassCanonicalType, int maxRecursionDepth, int parentMethodLine) {
        String baseMethodId;
        String methodId;
        PsiClass callMethodClass;
        String formatMethodId;
        String methodId2;
        ResolvedMethodCall methodCall;
        PsiMethod argMethod;
        PsiClass argClass;
        if (psiMethod == null || Arrays.stream(psiMethod.getParameterList().getParameters()).anyMatch(psiParameter -> {
            return Objects.isNull(psiParameter.getType());
        })) {
            return;
        }
        Collection<PsiCallExpression> psiMethodCallExpressions = PsiTreeUtil.findChildrenOfType(psiMethod, PsiCallExpression.class);
        baseMethodId = PsiUtils.formatMethodId(psiMethod.getContainingClass(), psiMethod.getName(), psiMethod.getParameterList().getParameters());
        for (PsiCallExpression psiCallExpression : psiMethodCallExpressions) {
            PsiMethod psiMethodResolved = psiCallExpression.resolveMethod();
            if (psiMethodResolved != null) {
                String name = getReturnTypeName(psiMethodResolved, psiCallExpression);
                methodId = PsiUtils.formatMethodId(psiMethodResolved.getContainingClass(), psiMethodResolved.getName(), psiMethodResolved.getParameterList().getParameters());
                boolean isStatic = psiMethodResolved.hasModifierProperty("static");
                if (!isNotNeedCalledMethod(psiMethodResolved.getName(), methodId, isStatic)) {
                    PsiExpression[] argumentList = psiCallExpression.getArgumentList().getExpressions();
                    ArrayList<MethodCallArg> methodCallArguments = new ArrayList<>();
                    PsiParameter[] parameters = psiMethodResolved.getParameterList().getParameters();
                    PsiType[] psiTypes = new PsiType[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        PsiParameter parameter = parameters[i];
                        PsiType parameterType = parameter.getType();
                        String parameterName = parameter.getName();
                        PsiExpression argument = null;
                        if (argumentList != null && i < argumentList.length) {
                            argument = argumentList[i];
                            psiTypes[i] = argument.getType() == null ? parameterType : argument.getType();
                        } else {
                            psiTypes[i] = parameterType;
                        }
                        if (argument != null && (argMethod = getCallMethod(argument)) != null && (argClass = getContainingClass(argument)) != null && checkChildMethodCall(psiMethod, getStaticSuperClass(argClass, psiMethodResolved).getQualifiedName(), ownerClassCanonicalType) && maxRecursionDepth > 0) {
                            findMethodCalls(methodCalled, argMethod, ownerClassCanonicalType, maxRecursionDepth - 1, parentMethodLine + argument.getTextRange().getStartOffset());
                        }
                        String callArg = argument == null ? parameter.getText() : argument.getText().trim();
                        if (callArg.contains("\"")) {
                            callArg = callArg.substring(1, callArg.length() - 1);
                        }
                        methodCallArguments.add(new MethodCallArg(callArg, parameterName, psiTypes[i]));
                    }
                    PsiType returnType = getReturnType(psiMethodResolved, psiCallExpression);
                    if (psiCallExpression instanceof PsiMethodCallExpression) {
                        PsiClass callMethodClass2 = getContainingClassFromMethodCall((PsiMethodCallExpression) psiCallExpression);
                        if (callMethodClass2 == null) {
                            callMethodClass2 = psiMethod.getContainingClass();
                        }
                        callMethodClass = getStaticSuperClass(callMethodClass2, psiMethodResolved);
                        methodId2 = PsiUtils.formatMethodId(callMethodClass, psiMethodResolved.getName(), psiTypes);
                        methodCall = new ResolvedMethodCall(psiMethodResolved, methodCallArguments, methodId2, callMethodClass, returnType, Integer.valueOf(parentMethodLine + psiCallExpression.getTextRange().getStartOffset()), name);
                    } else {
                        PsiClass callMethodClass3 = psiMethodResolved.getContainingClass();
                        callMethodClass = getStaticSuperClass(callMethodClass3, psiMethodResolved);
                        formatMethodId = PsiUtils.formatMethodId(psiMethodResolved.getContainingClass(), psiMethodResolved.getName(), psiMethodResolved.getParameterList().getParameters());
                        methodId2 = formatMethodId;
                        methodCall = new ResolvedMethodCall(psiMethodResolved, methodCallArguments, returnType, Integer.valueOf(parentMethodLine + psiCallExpression.getTextRange().getStartOffset()), "name");
                    }
                    String callMethodClassCanonicalType = callMethodClass == null ? "" : callMethodClass.getQualifiedName();
                    String finalMethodId = methodId2;
                    if (!methodCalled.contains(methodCall)) {
                        methodCalled.add(methodCall);
                    }
                    if (checkChildMethodCall(psiMethod, callMethodClassCanonicalType, ownerClassCanonicalType) && maxRecursionDepth > 0 && !finalMethodId.equals(baseMethodId)) {
                        findMethodCalls(methodCalled, psiMethodResolved, ownerClassCanonicalType, maxRecursionDepth - 1, parentMethodLine + psiCallExpression.getTextRange().getStartOffset());
                    }
                }
            }
        }
    }

    private static boolean isNotNeedCalledMethod(String methodName, String methodId, boolean isStatic) {
        return !isStatic ? TypeUtils.isIgnore(methodName) || methodId.startsWith("java.time.LocalDateTime#") || methodId.startsWith("java.time.LocalDate#") || methodId.startsWith("java.time.LocalTime#") : TypeUtils.isIgnore(methodName) || methodId.contains("java.util.List#toArray(") || methodId.contains("java.util.List#copyOf(") || methodId.contains("java.util.List#of(") || methodId.contains("java.util.List#spliterator(") || methodId.contains("java.util.List#subList(") || methodId.contains("java.util.List#listIterator(") || methodId.contains("java.util.List#lastIndexOf(") || methodId.contains("java.util.List#indexOf(") || methodId.contains("java.util.List#isEmpty()") || methodId.contains("java.util.Collection#size()") || methodId.contains("java.util.Collection#toArray(") || methodId.contains("java.util.Collection#containsAll(") || methodId.contains("java.util.Collection#iterator(") || methodId.contains("java.util.Collection#contains(") || methodId.contains("java.util.Collection#isEmpty()") || methodId.contains("java.util.Map#size()") || methodId.contains("java.util.Map#isEmpty()") || methodId.contains("java.util.Map#containsKey(") || methodId.contains("java.util.Map#containsValue(") || methodId.contains("java.util.Map#keySet()") || methodId.contains("java.util.Map#entrySet()") || methodId.contains("java.util.Map#values()") || methodId.startsWith("java.util.stream.Stream#") || methodId.contains("#copyProperties(");
    }

    @NotNull
    public static List<ResolvedReference> findReferences(PsiMethod psiMethod) {
        List<ResolvedReference> resolvedReferences = new ArrayList<>();
        Collection<PsiReferenceExpression> psiReferenceExpressions = PsiTreeUtil.findChildrenOfType(psiMethod, PsiReferenceExpression.class);
        for (PsiReferenceExpression psiReferenceExpression : psiReferenceExpressions) {
            PsiType refType = psiReferenceExpression.getType();
            PsiElement psiElement = psiReferenceExpression.resolve();
            if (refType != null && !(psiElement instanceof PsiMethod)) {
                PsiType psiOwnerType = psiReferenceExpression.getLastChild() == null ? null : resolveOwnerType(psiReferenceExpression.getLastChild());
                if (psiOwnerType != null) {
                    resolvedReferences.add(new ResolvedReference(psiReferenceExpression.getReferenceName(), refType, psiOwnerType));
                }
            }
        }
        if (resolvedReferences == null) {
            $$$reportNull$$$0(6);
        }
        return resolvedReferences;
    }

    @NotNull
    public static List<PsiMethod> findMethodReferences(PsiMethod psiMethod) {
        List<PsiMethod> resolvedReferences = new ArrayList<>();
        Collection<PsiJavaToken> psiJavaTokens = PsiTreeUtil.findChildrenOfType(psiMethod, PsiJavaToken.class);
        for (PsiJavaToken psiJavaToken : psiJavaTokens) {
            if (JavaTokenType.DOUBLE_COLON == psiJavaToken.getTokenType() && (psiJavaToken.getParent() instanceof PsiMethodReferenceExpression)) {
                PsiMethodReferenceExpression psiMethodReferenceExpression = psiJavaToken.getParent();
                PsiMethod resolve = psiMethodReferenceExpression.resolve();
                if (resolve instanceof PsiMethod) {
                    resolvedReferences.add(resolve);
                }
            }
        }
        if (resolvedReferences == null) {
            $$$reportNull$$$0(7);
        }
        return resolvedReferences;
    }

    private static PsiType resolveOwnerType(PsiElement psiElement) {
        boolean dotAppeared = false;
        PsiElement prevSibling = psiElement.getPrevSibling();
        while (true) {
            PsiElement prevSibling2 = prevSibling;
            if (prevSibling2 != null) {
                if (".".equals(prevSibling2.getText())) {
                    dotAppeared = true;
                } else if (dotAppeared && (prevSibling2 instanceof PsiExpression)) {
                    return ((PsiExpression) prevSibling2).getType();
                }
                prevSibling = prevSibling2.getPrevSibling();
            } else {
                return null;
            }
        }
    }

    private static PsiClass getStaticSuperClass(PsiClass callMethodClass, PsiMethod psiMethodResolved) {
        if (callMethodClass != null && psiMethodResolved.hasModifierProperty("static") && Arrays.stream(callMethodClass.getMethods()).noneMatch(method -> {
            return method.equals(psiMethodResolved);
        })) {
            PsiClass superClass = callMethodClass.getSuperClass();
            while (true) {
                PsiClass superClass2 = superClass;
                if (superClass2 == null) {
                    break;
                }
                if (Arrays.stream(superClass2.getMethods()).anyMatch(method2 -> {
                    return method2.equals(psiMethodResolved);
                })) {
                    return superClass2;
                }
                superClass = superClass2.getSuperClass();
            }
        }
        return callMethodClass;
    }

    public static PsiClass getContainingClassFromMethodCall(PsiMethodCallExpression methodCallExpression) {
        PsiReferenceExpression qualifierExpression = methodCallExpression.getMethodExpression().getQualifierExpression();
        if (qualifierExpression instanceof PsiReferenceExpression) {
            PsiVariable resolve = qualifierExpression.resolve();
            if (!(resolve instanceof PsiVariable)) {
                if (resolve instanceof PsiClass) {
                    return (PsiClass) resolve;
                }
                return null;
            }
            PsiClassType type = resolve.getType();
            if (type instanceof PsiClassType) {
                PsiClass resolvedClass = type.resolve();
                return resolvedClass;
            }
            return null;
        }
        PsiMethod resolve2 = methodCallExpression.getMethodExpression().resolve();
        if (resolve2 instanceof PsiMethod) {
            PsiClass psiClass = resolve2.getContainingClass();
            return psiClass;
        }
        return null;
    }

    private static PsiMethod getCallMethod(PsiExpression psiExpression) {
        if (psiExpression instanceof PsiReferenceExpression) {
            PsiMethod resolve = ((PsiReferenceExpression) psiExpression).resolve();
            if (resolve instanceof PsiMethod) {
                return resolve;
            }
            return null;
        }
        if (psiExpression instanceof PsiMethodCallExpression) {
            return ((PsiMethodCallExpression) psiExpression).resolveMethod();
        }
        if (psiExpression instanceof PsiCallExpression) {
            return ((PsiCallExpression) psiExpression).resolveMethod();
        }
        return null;
    }

    private static PsiClass getContainingClass(PsiExpression psiExpression) {
        if (!(psiExpression instanceof PsiMethodCallExpression)) {
            if (psiExpression instanceof PsiReferenceExpression) {
                PsiVariable resolve = ((PsiReferenceExpression) psiExpression).resolve();
                if (!(resolve instanceof PsiVariable)) {
                    if (resolve instanceof PsiClass) {
                        return (PsiClass) resolve;
                    }
                    return null;
                }
                PsiClassType type = resolve.getType();
                if (type instanceof PsiClassType) {
                    PsiClass resolvedClass = type.resolve();
                    return resolvedClass;
                }
                return null;
            }
            return null;
        }
        PsiMethodCallExpression methodCallExpression = (PsiMethodCallExpression) psiExpression;
        PsiReferenceExpression qualifierExpression = methodCallExpression.getMethodExpression().getQualifierExpression();
        if (qualifierExpression instanceof PsiReferenceExpression) {
            PsiVariable resolve2 = qualifierExpression.resolve();
            if (!(resolve2 instanceof PsiVariable)) {
                if (resolve2 instanceof PsiClass) {
                    return (PsiClass) resolve2;
                }
                return null;
            }
            PsiClassType type2 = resolve2.getType();
            if (type2 instanceof PsiClassType) {
                PsiClass resolvedClass2 = type2.resolve();
                return resolvedClass2;
            }
            return null;
        }
        PsiMethod resolve3 = methodCallExpression.getMethodExpression().resolve();
        if (resolve3 instanceof PsiMethod) {
            PsiClass psiClass = resolve3.getContainingClass();
            return psiClass;
        }
        return null;
    }

    private static boolean checkChildMethodCall(PsiMethod psiMethod, String callMethodClassCanonicalType, String ownerClassCanonicalType) {
        boolean isAbstract = !psiMethod.hasModifierProperty("abstract");
        boolean isNative = !psiMethod.hasModifierProperty("native");
        boolean isStatic = !psiMethod.hasModifierProperty("static");
        return isAbstract && isNative && isStatic && StringUtils.isNotEmpty(callMethodClassCanonicalType) && StringUtils.isNotEmpty(ownerClassCanonicalType) && callMethodClassCanonicalType.equals(ownerClassCanonicalType);
    }

    private static PsiType getReturnType(PsiMethod psiMethodResolved, PsiExpression psiMethodCallExpression) {
        PsiType returnType = null;
        if (psiMethodResolved != null) {
            returnType = psiMethodResolved.getReturnType();
            if (psiMethodCallExpression instanceof PsiCallExpression) {
                if (psiMethodCallExpression.getParent() != null && (psiMethodCallExpression.getParent() instanceof PsiTypeCastExpression)) {
                    PsiTypeCastExpression castExpression = psiMethodCallExpression.getParent();
                    if (castExpression.getCastType() != null) {
                        returnType = castExpression.getCastType().getType();
                    }
                } else {
                    PsiSubstitutor substitutor = ((PsiCallExpression) psiMethodCallExpression).resolveMethodGenerics().getSubstitutor();
                    if (returnType != null) {
                        returnType = substitutor.substitute(returnType);
                    }
                }
            }
        }
        if (returnType == null) {
            returnType = (PsiType) PsiUtils.getField("com.intellij.psi.PsiType", "VOID");
        }
        return returnType;
    }

    private static String getReturnTypeName(PsiMethod psiMethodResolved, PsiExpression psiMethodCallExpression) {
        String returnTypeName = "";
        if (psiMethodResolved != null && psiMethodResolved.getReturnType() != null) {
            returnTypeName = ClassNameUtils.extractClassName(ClassNameUtils.stripArrayVarargsDesignator(psiMethodResolved.getReturnType().getPresentableText()));
            if (psiMethodCallExpression instanceof PsiCallExpression) {
                PsiVariable parent = psiMethodCallExpression.getParent();
                if (parent instanceof PsiVariable) {
                    returnTypeName = parent.getName();
                } else if (parent instanceof PsiMethodCallExpression) {
                    PsiMethod callerMethod = ((PsiMethodCallExpression) parent).resolveMethod();
                    if (callerMethod == null || callerMethod.getParameterList().isEmpty()) {
                    }
                } else {
                    boolean isGetter = PropertyUtils.isPropertyGetter(psiMethodResolved);
                    if (isGetter) {
                        boolean isSetter = PropertyUtils.isPropertySetter(psiMethodResolved);
                        returnTypeName = ClassNameUtils.extractTargetPropertyName(psiMethodResolved.getName(), isSetter, isGetter);
                    }
                }
            }
        }
        return returnTypeName;
    }

    public static Set<String> findThrowException(PsiMethod psiMethod) {
        PsiExpression exceptionExpression;
        Set<String> exceptions = new HashSet<>();
        Collection<PsiThrowStatement> psiThrowStatements = PsiTreeUtil.findChildrenOfAnyType(psiMethod, new Class[]{PsiThrowStatement.class});
        for (PsiThrowStatement throwStatement : psiThrowStatements) {
            if (throwStatement != null && (exceptionExpression = throwStatement.getException()) != null) {
                PsiClassType type = exceptionExpression.getType();
                if (type instanceof PsiClassType) {
                    PsiClassType psiClass = type;
                    PsiClass resolve = psiClass.resolve();
                    exceptions.add(resolve.getQualifiedName());
                }
            }
        }
        return exceptions;
    }

    @Nullable
    public Method findValidConstructor(Type type, boolean hasEmptyConstructor) {
        Method foundCtor = null;
        Iterator<Method> it = type.findConstructors().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Method method = it.next();
            if (isValidConstructor(type, method, hasEmptyConstructor)) {
                foundCtor = method;
                break;
            }
        }
        return foundCtor;
    }

    public boolean isValidConstructor(Type type, Method constructor, boolean hasEmptyConstructor) {
        Type methodParamType;
        if (!constructor.isAccessible() || type.isInterface() || type.isAbstract()) {
            return false;
        }
        List<Param> methodParams = constructor.getMethodParams();
        for (Param methodParam : methodParams) {
            if (methodParam == null || (methodParamType = methodParam.getType()) == null) {
                return false;
            }
            if (methodParamType.equals(type) && hasEmptyConstructor) {
                return false;
            }
            if (methodParamType.isInterface() || methodParamType.isAbstract()) {
                if (hasEmptyConstructor) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String resolveBranchName(@NotNull PsiExpression condition, boolean boolValue) {
        if (condition == null) {
            $$$reportNull$$$0(8);
        }
        StringBuilder name = new StringBuilder();
        if (condition instanceof PsiBinaryExpression) {
            if (condition.getFirstChild() != null) {
                name.append(ClassNameUtils.extractMethodName(condition.getFirstChild().getText()));
            }
            name.append(boolValue ? "Is" : "Not");
            if (condition.getLastChild() != null) {
                name.append(ClassNameUtils.extractMethodName(condition.getLastChild().getText()));
            }
        } else if (condition instanceof PsiMethodCallExpression) {
            PsiMethodCallExpression methodCall = (PsiMethodCallExpression) condition;
            String methodName = ClassNameUtils.extractMethodName(methodCall.getMethodExpression().getText());
            name.append(methodName);
            if (!boolValue) {
                name.append("Not");
            }
            PsiExpressionList argumentList = methodCall.getArgumentList();
            for (PsiExpression expression : argumentList.getExpressions()) {
                name.append(ClassNameUtils.extractMethodName(expression.getText()));
            }
        } else {
            name.append(ClassNameUtils.extractMethodName(condition.getText()));
        }
        return name.toString();
    }

    public static int getLineCount(PsiFile psiFile) {
        Document document;
        VirtualFile virtualFile = psiFile.getVirtualFile();
        if (virtualFile != null && (document = FileDocumentManager.getInstance().getDocument(virtualFile)) != null) {
            return document.getLineCount();
        }
        return 0;
    }

    public static int getLineCount(PsiMethod psiMethod) {
        String methodText = psiMethod.getText();
        if (StringUtils.isBlank(methodText)) {
            return 0;
        }
        String[] lines = methodText.split("\n");
        return lines.length;
    }

    public static Type resolveChildTypeIfNeeded(Type type, boolean replaceInterfaceParamsWithConcreteTypes, int maxNumOfConcreteCandidatesToReplaceInterfaceParam, Module srcModule, TypeDictionary typeDictionary, Map<String, String> DEFAULT_TYPE_TO_BOCOM) {
        if (!isConcreteType(type) && replaceInterfaceParamsWithConcreteTypes) {
            Type childType = findChildType(type, 3, maxNumOfConcreteCandidatesToReplaceInterfaceParam, srcModule, typeDictionary, DEFAULT_TYPE_TO_BOCOM);
            if (childType == null) {
                return type;
            }
            return childType;
        }
        return type;
    }

    private static Type findChildType(Type type, int maxRecursionDepth, int maxNumOfConcreteCandidatesToReplaceInterfaceParam, Module srcModule, TypeDictionary typeDictionary, Map<String, String> DEFAULT_TYPE_TO_BOCOM) {
        Type grandChild;
        PsiClass psiClass = (PsiClass) ApplicationManager.getApplication().runReadAction(() -> {
            return findClassInModule(type.getCanonicalName(), srcModule);
        });
        if (psiClass != null) {
            if (!TypeUtils.isArrayType(type) && !TypeUtils.isMap(type.getCanonicalName()).booleanValue()) {
                List<PsiClass> psiClassList = new ArrayList<>();
                if (DEFAULT_TYPE_TO_BOCOM.containsKey(type.getCanonicalName())) {
                    JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(srcModule.getProject());
                    GlobalSearchScope scope = GlobalSearchScope.allScope(srcModule.getProject());
                    PsiClass classes = javaPsiFacade.findClass(DEFAULT_TYPE_TO_BOCOM.get(type.getCanonicalName()), scope);
                    if (classes != null) {
                        psiClassList.add(classes);
                    }
                } else {
                    Object[] childElements = new SubtypesHierarchyTreeStructure(srcModule.getProject(), psiClass, "All").getChildElements(new TypeHierarchyNodeDescriptor(srcModule.getProject(), (HierarchyNodeDescriptor) null, psiClass, true));
                    psiClassList.addAll(getPsiClasses(childElements, srcModule, type.getPackageName()));
                }
                if (!psiClassList.isEmpty()) {
                    int size = Math.min(psiClassList.size(), maxNumOfConcreteCandidatesToReplaceInterfaceParam);
                    for (int i = 0; i < size; i++) {
                        PsiClass childPsiClass = psiClassList.get(i);
                        PsiClassType psiChildType = JavaPsiFacade.getInstance(srcModule.getProject()).getElementFactory().createType(childPsiClass);
                        Type childType = typeDictionary.getType((PsiType) psiChildType, maxRecursionDepth, true);
                        if (childType != null) {
                            if (isConcreteType(childType)) {
                                DEFAULT_TYPE_TO_BOCOM.put(type.getCanonicalName(), childType.getCanonicalName());
                                return childType;
                            }
                            if (maxRecursionDepth > 0 && (grandChild = findChildType(childType, maxRecursionDepth - 1, maxNumOfConcreteCandidatesToReplaceInterfaceParam, srcModule, typeDictionary, DEFAULT_TYPE_TO_BOCOM)) != null) {
                                DEFAULT_TYPE_TO_BOCOM.put(type.getCanonicalName(), grandChild.getCanonicalName());
                                return grandChild;
                            }
                        }
                    }
                    return null;
                }
                return null;
            }
            return type;
        }
        return null;
    }

    private static int comparePackageName(String packageName, String qualifiedName) {
        if (StringUtils.isEmpty(packageName) || StringUtils.isEmpty(qualifiedName)) {
            return 0;
        }
        String[] arr1 = packageName.split("\\.");
        String[] arr2 = qualifiedName.split("\\.");
        for (int i = 0; i < arr1.length; i++) {
            if (arr2.length == i) {
                return i;
            }
            if (!org.apache.commons.lang3.StringUtils.equalsIgnoreCase(arr1[i], arr2[i])) {
                return i;
            }
        }
        return arr1.length;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public static PsiClass findClassInModule(String fqName, Module srcModule) {
        PsiClass psiClass = JavaPsiFacade.getInstance(srcModule.getProject()).findClass(fqName, srcModule.getModuleRuntimeScope(true));
        if (psiClass == null && fqName.contains("<")) {
            return JavaPsiFacade.getInstance(srcModule.getProject()).findClass(fqName.substring(0, fqName.indexOf("<")), srcModule.getModuleRuntimeScope(true));
        }
        return psiClass;
    }

    @NotNull
    private static List<PsiClass> getPsiClasses(Object[] childElements, Module srcModule, String packageName) {
        List<PsiClass> psiClassList = new ArrayList<>();
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(srcModule.getProject());
        for (Object childElement : childElements) {
            if (childElement instanceof TypeHierarchyNodeDescriptor) {
                TypeHierarchyNodeDescriptor hierarchyNodeDescriptor = (TypeHierarchyNodeDescriptor) childElement;
                if (hierarchyNodeDescriptor.getPsiClass() instanceof PsiClass) {
                    PsiClass childPsiClass = hierarchyNodeDescriptor.getPsiClass();
                    Boolean isAbstract = Boolean.valueOf((childPsiClass == null || childPsiClass.getModifierList() == null || !childPsiClass.getModifierList().hasModifierProperty("abstract")) ? false : true);
                    if (childPsiClass != null && childPsiClass.getQualifiedName() != null && !childPsiClass.isInterface() && !isAbstract.booleanValue()) {
                        if (javaPsiFacade.findClass(childPsiClass.getQualifiedName(), GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(srcModule)) != null) {
                            psiClassList.add(childPsiClass);
                        } else {
                            String shortName = ClassNameUtils.extractClassName(childPsiClass.getQualifiedName());
                            PsiClass[] psiClasses = PsiShortNamesCache.getInstance(srcModule.getProject()).getClassesByName(shortName, GlobalSearchScope.moduleScope(srcModule));
                            if (psiClasses.length > 0) {
                                psiClassList.add(childPsiClass);
                            }
                        }
                    }
                }
            }
        }
        psiClassList.sort((a, b) -> {
            String qualifiedName = ClassNameUtils.extractPackageName(a.getQualifiedName());
            int a1 = comparePackageName(packageName, qualifiedName);
            String qualifiedName1 = ClassNameUtils.extractPackageName(b.getQualifiedName());
            int b1 = comparePackageName(packageName, qualifiedName1);
            return b1 - a1;
        });
        if (psiClassList == null) {
            $$$reportNull$$$0(9);
        }
        return psiClassList;
    }

    public static boolean isConcreteType(Type type) {
        return (type.isInterface() || type.isAbstract()) ? false : true;
    }
}
