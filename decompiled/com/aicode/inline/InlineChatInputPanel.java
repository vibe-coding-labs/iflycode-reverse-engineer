package com.aicode.inline;

import cn.hutool.core.util.IdUtil;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.CodeModel;
import com.aicode.agent.dto.FunctionModelInfo;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.agent.service.UserService;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.diff.GenericUtils;
import com.aicode.icons.Icons;
import com.aicode.inline.InlineChatService;
import com.aicode.inline.content.ChatMessage;
import com.aicode.inline.controller.ChatInputController;
import com.aicode.inline.controller.EphemeralChatSessionController;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.editor.CancelRequestTip;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.ui.SendStopActionButtonPanel;
import com.aicode.util.EditorCacheUtil;
import com.intellij.ide.BrowserUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.MessageType;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import kotlin.Unit;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: zh */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/InlineChatInputPanel.class */
public class InlineChatInputPanel extends JPanel {

    /* renamed from: new, reason: not valid java name */
    @NotNull
    private final GridBagConstraints f331new;

    /* renamed from: long, reason: not valid java name */
    @NotNull
    private final InlineChatInputComponent f332long;

    /* renamed from: super, reason: not valid java name */
    @NotNull
    private final InlineChatPanel f333super;

    /* renamed from: for, reason: not valid java name */
    @NotNull
    private final GridBagConstraints f334for;

    /* renamed from: if, reason: not valid java name */
    @NotNull
    private GridBagConstraints f335if;

    /* renamed from: case, reason: not valid java name */
    @NotNull
    private final ChatInputController f336case;

    /* renamed from: final, reason: not valid java name */
    @NotNull
    private final Editor f337final;

    /* renamed from: try, reason: not valid java name */
    @NotNull
    private final SendStopActionButtonPanel f338try;

    /* renamed from: float, reason: not valid java name */
    @NotNull
    private final EphemeralChatSessionController f339float;

    /* renamed from: byte, reason: not valid java name */
    @NotNull
    private ComboBox f340byte;

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final GridBagConstraints f341enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m170enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 4:
            case 5:
            case 6:
            default:
                H = OpenTelemetryUtil.H("\u000fEG\u0018A>a%(!j?l/FF,>.afqE\u0019|?1;{$5#T\u0001u6akj5a%");
                i = a;
                break;
            case 2:
            case 3:
                do {
                } while (0 != 0);
                H = OpenTelemetryUtil.H("O8b4i%!\u007f(*`9-\tF#{\u0005q,#+X\r}*`,|)}k#eQA)\"fd0\"\u0006I|k| g$5?^\u0001 &jkj5a%");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 4:
            case 5:
            case 6:
            default:
                i2 = 2;
                break;
            case 2:
            case 3:
                i2 = 3;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 1:
            case 4:
            case 5:
            case 6:
            default:
                objArr[0] = OpenTelemetryUtil.H("l$`fi%l$`%\r\u000fg!i*p~a\u0002c\"\u007f0W8t%x\u001bp1{\u001be.h%");
                i3 = a;
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = OpenTelemetryUtil.H("<z<|?T6h%{\u001be.h%");
                i3 = a;
                break;
            case 3:
                objArr[0] = OpenTelemetryUtil.H("j/m4b;");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = OpenTelemetryUtil.H("h.e\u001cz<|?T6h%{\u001be.h%");
                i4 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = OpenTelemetryUtil.H("\u0012e0J/m4b;");
                i4 = a;
                break;
            case 2:
            case 3:
                objArr[1] = OpenTelemetryUtil.H("l$`fi%l$`%\r\u000fg!i*p~a\u0002c\"\u007f0W8t%x\u001bp1{\u001be.h%");
                i4 = a;
                break;
            case 4:
                objArr[1] = OpenTelemetryUtil.H(",t!]>e$E6o)\u007f$j%c=");
                i4 = a;
                break;
            case 5:
                objArr[1] = OpenTelemetryUtil.H("s5a\u0013D\u0001t+a\u001be.h%");
                i4 = a;
                break;
            case 6:
                objArr[1] = OpenTelemetryUtil.H("r4\\/g*e\u001cz `%r\u001an0}$h,h;");
                i4 = a;
                break;
        }
        switch (i4) {
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[2] = OpenTelemetryUtil.H("3\"j)yw");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 4:
            case 5:
            case 6:
            default:
                throw new IllegalStateException(format);
            case 2:
            case 3:
                throw new IllegalArgumentException(format);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public InlineChatInputPanel(@NotNull InlineChatPanel inlineChatPanel, @NotNull Editor a) {
        super(new GridBagLayout());
        if (inlineChatPanel == null) {
            m170enum(2);
        }
        if (a == null) {
            m170enum(3);
        }
        this.f340byte = null;
        this.f333super = inlineChatPanel;
        this.f337final = a;
        this.f332long = new InlineChatInputComponent(this);
        setComboBox();
        this.f338try = new SendStopActionButtonPanel(() -> {
            this.f336case.submit();
            a.putUserData(EditorCacheUtil.f673byte, (Object) null);
            return Unit.INSTANCE;
        }, () -> {
            this.f336case.stop();
            return Unit.INSTANCE;
        });
        this.f339float = new EphemeralChatSessionController(this);
        this.f336case = new ChatInputController(this.f332long, a2 -> {
            if (CE()) {
                InlineChatService.Companion.closeInlineChat(a);
                return Unit.INSTANCE;
            }
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setQuestion(a2);
            this.f339float.executeRequest(chatMessage, a);
            return Unit.INSTANCE;
        });
        this.f338try.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 8));
        this.f341enum = new GridBagConstraints();
        this.f341enum.gridx = 0;
        this.f341enum.gridy = 0;
        this.f341enum.fill = 0;
        this.f341enum.anchor = 12;
        this.f334for = new GridBagConstraints();
        this.f334for.gridx = 1;
        this.f334for.gridy = 0;
        this.f334for.fill = 2;
        this.f334for.anchor = 10;
        this.f334for.weightx = 1.0d;
        if (this.f340byte != null) {
            this.f335if = new GridBagConstraints();
            this.f335if.gridx = 2;
            this.f335if.gridy = 0;
            this.f335if.fill = 0;
            this.f335if.anchor = 13;
        }
        this.f331new = new GridBagConstraints();
        this.f331new.gridx = this.f340byte != null ? 3 : 2;
        this.f331new.gridy = 0;
        this.f331new.fill = 0;
        this.f331new.anchor = 15;
        setOpaque(false);
        JLabel jLabel = new JLabel(Icons.I_FLY_CODE);
        jLabel.setBorder(BorderFactory.createEmptyBorder(11, 8, 11, 0));
        add(jLabel, this.f341enum);
        add(this.f332long, this.f334for);
        if (this.f340byte != null) {
            add(this.f340byte, this.f335if);
        }
        add(this.f338try, this.f331new);
        this.f332long.getDocument().addDocumentListener(new DocumentListener() { // from class: com.aicode.inline.InlineChatInputPanel.01
            public void insertUpdate(DocumentEvent documentEvent) {
                InlineChatInputPanel.this.getInputComponent().updatePlaceholder();
                InlineChatInputPanel.this.f338try.showSendButton(() -> {
                    return Boolean.valueOf(!InlineChatInputPanel.this.getInputComponent().getText().isEmpty());
                });
            }

            public void changedUpdate(DocumentEvent documentEvent) {
                InlineChatInputPanel.this.getInputComponent().updatePlaceholder();
                InlineChatInputPanel.this.f338try.showSendButton(() -> {
                    return Boolean.valueOf(!InlineChatInputPanel.this.getInputComponent().getText().isEmpty());
                });
            }

            public void removeUpdate(DocumentEvent documentEvent) {
                InlineChatInputPanel.this.getInputComponent().updatePlaceholder();
                InlineChatInputPanel.this.f338try.showSendButton(() -> {
                    return Boolean.valueOf(!InlineChatInputPanel.this.getInputComponent().getText().isEmpty());
                });
            }
        });
    }

    @NotNull
    public final Editor getEditor() {
        Editor editor = this.f337final;
        if (editor == null) {
            m170enum(1);
        }
        return editor;
    }

    private void qA(String a) {
        ApplicationManager.getApplication().invokeLater(() -> {
            Notification title = NotificationGroupManager.getInstance().getNotificationGroup(OpenTelemetryUtil.H("1|2^\u0011eja$p)n,")).createNotification("本月" + a + "次数已用尽，到个人中心查看详情", MessageType.INFO).setTitle(BasicActionsBundle.message(OpenTelemetryUtil.H(".z#z;*!K\u0005f)ejP5A\u0018`9P6`9z?v\u0007o1\u007fep%u="), new Object[0]));
            if (StringUtils.isNotBlank(AICodeSettingsState.getInstance().userCenterWebUrl)) {
                title.addAction(new S(OpenTelemetryUtil.H("甬扷丠徊")));
            }
            title.notify(this.f337final.getProject());
        });
    }

    @NotNull
    public ChatInputController getChatInputController() {
        ChatInputController chatInputController = this.f336case;
        if (chatInputController == null) {
            m170enum(6);
        }
        return chatInputController;
    }

    @NotNull
    public final InlineChatPanel getInlineChatPanel() {
        InlineChatPanel inlineChatPanel = this.f333super;
        if (inlineChatPanel == null) {
            m170enum(0);
        }
        return inlineChatPanel;
    }

    /* compiled from: zh */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/InlineChatInputPanel$S.class */
    class S extends NotificationAction {
        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m171enum(int a) {
            String H = CancelRequestTip.H(")\u001a\n\u0018\u0007\u000f\u000f\u0015\nL\"?J*&\u0007\u0019#\u001f\u0006\rA7&\u001e\r\b��\u0004\u0015?mMO\u0007SQ\u001e\u0016Pq'K@\u0019J\f\u0014\u001b\u001c��N8#A\u0003\u000fJ\u001f\u0004\u0005\u0005");
            Object[] objArr = new Object[3];
            switch (a) {
                case 0:
                default:
                    objArr[0] = GenericUtils.H("6");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[0] = CancelRequestTip.H("NO#>\u0007\b\t\u000b\u0005\u0018\u0006\u0007");
                    break;
            }
            objArr[1] = GenericUtils.H("\u0016\u00123y66!%\u001b\u0012w9(\"*%'e/��;665\u00103;&[t\u0015\u0018'\u000b9>&'\u007fa");
            objArr[2] = CancelRequestTip.H("��\u000b\u001cIO9\u0007\u0004\u0013\f\u0005\u0003\u001c\f\r");
            throw new IllegalArgumentException(String.format(H, objArr));
        }

        public void actionPerformed(@NotNull AnActionEvent e, @NotNull Notification a) {
            if (e == null) {
                m171enum(0);
            }
            if (a == null) {
                m171enum(1);
            }
            if (StringUtils.isNotBlank(AICodeSettingsState.getInstance().userCenterWebUrl)) {
                BrowserUtil.browse(AICodeSettingsState.getInstance().userCenterWebUrl);
            }
        }

        public S(String a) {
            super(a);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean CE() {
        FunctionModelInfo functionModelInfo;
        FunctionModelInfo functionModelInfo2 = null;
        Iterator<FunctionModelInfo> it = AICodeSettingsState.getInstance().modelInfoList.iterator();
        while (true) {
            if (it.hasNext()) {
                FunctionModelInfo next = it.next();
                if (PermissionEnum.INLINE_CHAT.getPermission().equalsIgnoreCase(next.getPermissionCode())) {
                    functionModelInfo = next;
                    functionModelInfo2 = functionModelInfo;
                    break;
                }
            } else {
                functionModelInfo = null;
                break;
            }
        }
        if (functionModelInfo != null && !CollectionUtils.isEmpty(functionModelInfo2.getCodeModelList())) {
            for (CodeModel codeModel : functionModelInfo2.getCodeModelList()) {
                if (AICodeSettingsState.getInstance().inlineChatModelCode.equals(codeModel.getModelCode()) && codeModel.isTokenExhausted()) {
                    qA(codeModel.getOriginalModelName());
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @NotNull
    public final SendStopActionButtonPanel getButtonPanel() {
        SendStopActionButtonPanel sendStopActionButtonPanel = this.f338try;
        if (sendStopActionButtonPanel == null) {
            m170enum(5);
        }
        return sendStopActionButtonPanel;
    }

    @NotNull
    public InlineChatInputComponent getInputComponent() {
        InlineChatInputComponent inlineChatInputComponent = this.f332long;
        if (inlineChatInputComponent == null) {
            m170enum(4);
        }
        return inlineChatInputComponent;
    }

    public final void delete() {
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void setComboBox() {
        FunctionModelInfo functionModelInfo;
        this.f340byte = new ComboBox();
        this.f340byte.setPreferredSize(new Dimension(170, 30));
        this.f340byte.addPopupMenuListener(new PopupMenuListener() { // from class: com.aicode.inline.InlineChatInputPanel.03
            public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {
            }

            public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
                MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.USER_MODEL_LIST.getType());
                messageDto.setOtherObject(InlineChatInputPanel.this.f340byte);
                PluginWebsocketClient.sendWsMessage(messageDto, InlineChatInputPanel.this.f337final.getProject());
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {
            }
        });
        if (AICodeSettingsState.getInstance().modelInfoList == null) {
            this.f340byte.setVisible(false);
            return;
        }
        FunctionModelInfo functionModelInfo2 = null;
        Iterator<FunctionModelInfo> it = AICodeSettingsState.getInstance().modelInfoList.iterator();
        while (true) {
            if (it.hasNext()) {
                FunctionModelInfo next = it.next();
                if (PermissionEnum.INLINE_CHAT.getPermission().equalsIgnoreCase(next.getPermissionCode())) {
                    functionModelInfo = next;
                    functionModelInfo2 = functionModelInfo;
                    break;
                }
            } else {
                functionModelInfo = null;
                break;
            }
        }
        if (functionModelInfo != null && !CollectionUtils.isEmpty(functionModelInfo2.getCodeModelList())) {
            List<CodeModel> codeModelList = functionModelInfo2.getCodeModelList();
            if (codeModelList.size() == 1) {
                AICodeSettingsState.getInstance().inlineChatModelCode = codeModelList.get(0).getModelCode();
                this.f340byte.setVisible(false);
                return;
            } else {
                UserService.setItem(this.f340byte, codeModelList);
                this.f340byte.addItemListener(a -> {
                    CodeModel codeModel = (CodeModel) a.getItem();
                    AICodeSettingsState.getInstance().inlineChatModelCode = codeModel.getModelCode();
                });
                this.f340byte.setVisible(true);
                return;
            }
        }
        this.f340byte.setVisible(false);
    }
}
