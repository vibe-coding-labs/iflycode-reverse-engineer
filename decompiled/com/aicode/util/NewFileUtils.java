package com.aicode.util;

import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.service.CodeCompleteService;
import com.aicode.enums.CodeCollectEnum;
import com.aicode.enums.LanguageEnum;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.editor.EditorManagerServiceImpl;
import com.aicode.settings.UnitTestSettingsState;
import com.aicode.test.UnitTestService;
import com.aicode.test.dto.UnitTestDto;
import com.google.gson.JsonObject;
import com.intellij.ide.util.EditorHelper;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import java.awt.Dimension;
import java.util.Iterator;
import javax.swing.BorderFactory;

/* compiled from: pa */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/NewFileUtils.class */
public class NewFileUtils {
    public static /* synthetic */ String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getClassName()).append(stackTraceElement.getMethodName()).toString();
        int length = stringBuffer.length() - 1;
        int i = (2 << 3) ^ 4;
        int i2 = (3 << 3) ^ 4;
        int i3 = (4 << 4) ^ ((2 ^ 5) << 1);
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i4 = length2 - 1;
        int i5 = i4;
        int i6 = length;
        while (i4 >= 0) {
            int i7 = i5;
            int i8 = i5 - 1;
            cArr[i7] = (char) (i2 ^ (str.charAt(i7) ^ stringBuffer.charAt(i6)));
            if (i8 < 0) {
                break;
            }
            char charAt = (char) (i3 ^ (str.charAt(i8) ^ stringBuffer.charAt(i6)));
            i5 = i8 - 1;
            i6--;
            cArr[i8] = charAt;
            if (i6 < 0) {
                i6 = length;
            }
        }
        return new String(cArr);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ void handleCreateFile(Project a, String a2, String a3, String a4, String a5, CodeCollectEnum a6) {
        if (!org.apache.commons.lang3.StringUtils.equals(CodeCompleteService.H("cjMi"), a5)) {
            creatFile(a, a2, a3, a4, a6);
            return;
        }
        FileChooserDescriptor createSingleFolderDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor();
        createSingleFolderDescriptor.setForcedToUseIdeaFileChooser(true);
        TextFieldWithBrowseButton textFieldWithBrowseButton = new TextFieldWithBrowseButton();
        textFieldWithBrowseButton.setText(a.getBasePath());
        textFieldWithBrowseButton.addBrowseFolderListener(new TextBrowseFolderListener(createSingleFolderDescriptor, a));
        JBTextField jBTextField = new JBTextField(a3);
        jBTextField.setPreferredSize(new Dimension(400, jBTextField.getPreferredSize().height));
        if (showDialog(a, textFieldWithBrowseButton, jBTextField, null, BasicActionsBundle.message(HandleCacheUtil.H("J\u001dc0v(\r\u001do'`9nqS\u001dt/x6O?z,"), new Object[0]), BasicActionsBundle.message(CodeCompleteService.H("d@o`N[5J~HiLx\u0007jcC@.D`Zo"), new Object[0])).show() == 0) {
            creatFile(a, a2, jBTextField.getText(), textFieldWithBrowseButton.getText(), a6);
        }
    }

    public static /* synthetic */ void creatFile(Project a, String a2, String a3, String a4, CodeCollectEnum a5) {
        VirtualFile refreshAndFindFileByIoFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(com.aicode.content.util.file.FileUtils.createFile(a4, a3, a2));
        if (refreshAndFindFileByIoFile != null) {
            PsiFile findFile = PsiManager.getInstance(a).findFile(refreshAndFindFileByIoFile);
            if (findFile == null) {
                throw new RuntimeException(HandleCacheUtil.H("R!y?m8l`\u000b\u0012i1vmW\u0014isz7v:TOa=eso?z,"));
            }
            EditorHelper.openInEditor(findFile);
            EditorManagerServiceImpl.acceptCount(a, refreshAndFindFileByIoFile.getPath(), a2, a5);
            return;
        }
        throw new RuntimeException(CodeCompleteService.H("I@PlDk\u000ex9ZQsA TmZ:\\kH~M,[aJi\u007fNI FlEi"));
    }

    public static /* synthetic */ void handleCreateFile(Project a, UnitTestDto.DataDTO a2, String a3, String a4, String a5, CodeCollectEnum a6) {
        FileChooserDescriptor createSingleFolderDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor();
        createSingleFolderDescriptor.setForcedToUseIdeaFileChooser(true);
        TextFieldWithBrowseButton textFieldWithBrowseButton = new TextFieldWithBrowseButton();
        textFieldWithBrowseButton.setText(a4);
        textFieldWithBrowseButton.addBrowseFolderListener(new TextBrowseFolderListener(createSingleFolderDescriptor, a));
        JBTextField jBTextField = new JBTextField(a3);
        jBTextField.setPreferredSize(new Dimension(400, jBTextField.getPreferredSize().height));
        JBCheckBox jBCheckBox = new JBCheckBox(BasicActionsBundle.message(HandleCacheUtil.H("b1n9\"s>-p:o;'#%}_Zt:a9\r\u000fm%lxp>D\u0007?-c=}3x="), new Object[0]), UnitTestSettingsState.getInstance().savePath);
        jBCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 118, 0, 0));
        DialogBuilder showDialog = showDialog(a, textFieldWithBrowseButton, jBTextField, jBCheckBox, BasicActionsBundle.message(CodeCompleteService.H("yKoKaE)]cIq\u0007xjYJ5J~HiLxoefJ\u000btIqEi"), new Object[0]), BasicActionsBundle.message(HandleCacheUtil.H("({j=e4(;k!b6.*%}dae+\u007f''\".g_Zm0v8O\u0019\"7`$e<D��c7\"'`\"z,"), new Object[0]));
        DialogWrapper dialogWrapper = showDialog.getDialogWrapper();
        showDialog.setOkOperation(() -> {
            DialogWrapper dialogWrapper2;
            String text = jBTextField.getText();
            if (org.apache.commons.lang3.StringUtils.isBlank(text)) {
                showDialog.setErrorText(BasicActionsBundle.message(HandleCacheUtil.H("c0%ry(?;b:}x?qX��.<`(B\bi\u0015`:eqU\u001dc!~}}3n="), new Object[0]), jBTextField);
                return;
            }
            String text2 = textFieldWithBrowseButton.getText();
            if (org.apache.commons.lang3.StringUtils.isBlank(text2)) {
                showDialog.setErrorText(BasicActionsBundle.message(CodeCompleteService.H("o\r)_u_3PnIqL3gT]\"XloNQeflEi7YJoJr\u000eqZbq\u0012"), new Object[0]), textFieldWithBrowseButton.getTextField());
                return;
            }
            UnitTestSettingsState.getInstance().savePath = jBCheckBox.isSelected();
            if (!UnitTestSettingsState.getInstance().savePath) {
                UnitTestSettingsState.getInstance().testClasPath = "";
                dialogWrapper2 = dialogWrapper;
            } else {
                UnitTestSettingsState.getInstance().testClasPath = textFieldWithBrowseButton.getText();
                dialogWrapper2 = dialogWrapper;
            }
            dialogWrapper2.close(0);
            a2.setTestClasPath(text2);
            a2.setTestClassName(text);
            if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(LanguageEnum.JAVA.getDescription(), a5)) {
                UnitTestService.saveUnitTestFile(a, a2);
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            Iterator<UnitTestDto.DataDTO.FunctionDataDTO> it = a2.getFunctionData().iterator();
            while (it.hasNext()) {
                UnitTestDto.DataDTO.FunctionDataDTO next = it.next();
                it = it;
                stringBuffer.append(next.getCodeContent());
            }
            creatFile(a, stringBuffer.toString(), text, text2, a6);
        });
        showDialog.setCancelOperation(() -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(CodeCompleteService.H("Kc{K"), WebViewDataTypeEnum.UNIT_TESTING_RESPONSE_SAVE.getType());
            SocketMessageHandleListener.send2Web(a, jsonObject);
            dialogWrapper.close(1);
        });
        showDialog.show();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String getChooseFile(Project a) {
        VirtualFile chooseFile = FileChooser.chooseFile(new FileChooserDescriptor(true, false, false, false, false, false).withFileFilter(a2 -> {
            String extension = a2.getExtension();
            return extension != null && (extension.equalsIgnoreCase(HandleCacheUtil.H("2y*")) || extension.equalsIgnoreCase(CodeCompleteService.H("[udZ")) || extension.equalsIgnoreCase(HandleCacheUtil.H("{-")));
        }), a, (VirtualFile) null);
        if (chooseFile == null) {
            return null;
        }
        return chooseFile.getPath();
    }

    public static /* synthetic */ DialogBuilder showDialog(Project a, TextFieldWithBrowseButton a2, JBTextField a3, JBCheckBox a4, String a5, String a6) {
        FormBuilder createFormBuilder = FormBuilder.createFormBuilder();
        createFormBuilder.addLabeledComponent(BasicActionsBundle.message(CodeCompleteService.H("dVyP~A/@t^\u007f|H\u000bfDdE+MgFk"), new Object[0]), a3).addLabeledComponent(a6, a2);
        if (a4 != null) {
            createFormBuilder.addComponent(a4);
        }
        DialogBuilder centerPanel = new DialogBuilder(a).title(a5).centerPanel(createFormBuilder.getPanel());
        centerPanel.addOkAction();
        centerPanel.addCancelAction();
        return centerPanel;
    }
}
