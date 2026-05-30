package com.aicode.util;

import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.exception.RequestTimeoutException;
import com.intellij.util.ui.JBInsets;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.border.Border;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: rb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/JComponentKt.class */
public final class JComponentKt {
    public static /* synthetic */ String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).insert(0, stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = (1 << 3) ^ 2;
        int i2 = 5 << 3;
        int i3 = (5 << 3) ^ 4;
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i4 = length2 - 1;
        int i5 = i4;
        int i6 = length;
        while (i4 >= 0) {
            int i7 = i5;
            int i8 = i5 - 1;
            cArr[i7] = (char) (i ^ (str.charAt(i7) ^ stringBuffer.charAt(i6)));
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

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m410enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 8:
            case 9:
            case 11:
            case 13:
            case 15:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            default:
                H = RequestTimeoutException.H("\u001a\b'\u00145\u001c8\u0003z\u001d7\u000b;z\u001a\u001a(3#\u001b6[!\u0011\"\u0010v_4\u00043@{X*_;U\u0019~S$t^+Y(\u0011)\u000f{\u0014?\u0005{\u0018$@\u0010*-\f");
                i = a;
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 10:
            case 12:
            case 14:
            case 16:
            case 18:
                do {
                } while (0 != 0);
                H = MethodGeneratorConfig.H("\u0001\u0001/:\u0013&4::y\u001b\u0004\u001f\u0016?u|$jo(u7!\"+z:/:_\u0003%:$-4t/:5;");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 8:
            case 9:
            case 11:
            case 13:
            case 15:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            default:
                i2 = 3;
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 10:
            case 12:
            case 14:
            case 16:
            case 18:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = RequestTimeoutException.H("\u007f\u000e8\u0018(^4\u0010\u001a>5\u0005");
                i3 = a;
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 10:
            case 12:
            case 14:
            case 16:
            case 18:
                do {
                } while (0 != 0);
                objArr[0] = MethodGeneratorConfig.H("8:4x%#8:>1~*.=,a52/#!041/;\u0012#");
                i3 = a;
                break;
            case 2:
                objArr[0] = RequestTimeoutException.H("]1\f3\b\u007f\u00179\u001f2\u00174\r-6;\u0005");
                i3 = a;
                break;
            case 4:
                objArr[0] = MethodGeneratorConfig.H("p%73'd>\r\u0014&+#-?0\u0012&#2");
                i3 = a;
                break;
            case 6:
                objArr[0] = RequestTimeoutException.H("]1\f3\b\u007f\u00171\t2\u00174\r-6;\u0005");
                i3 = a;
                break;
            case 8:
                objArr[0] = MethodGeneratorConfig.H("d:96)p' 7#");
                i3 = a;
                break;
            case 9:
                objArr[0] = RequestTimeoutException.H("\u00180/\u0014");
                i3 = a;
                break;
            case 11:
                objArr[0] = MethodGeneratorConfig.H("[\u0005('\"{8;3+<%");
                i3 = a;
                break;
            case 13:
                objArr[0] = RequestTimeoutException.H("|\r-\r)_)\u001f=\u001e-\u001f\b\u000e\r:5\u0013");
                i3 = a;
                break;
            case 15:
                objArr[0] = MethodGeneratorConfig.H("q-?-9\u007f957:\u00125!3+6\u001f4+#>9 ( 7$");
                i3 = a;
                break;
            case 17:
                objArr[0] = RequestTimeoutException.H("\u007f\u000e8\u0018(^.\u0010\u001f.4\u0005");
                i3 = a;
                break;
            case 19:
                objArr[0] = MethodGeneratorConfig.H("1\u0017-.*$");
                i3 = a;
                break;
            case 20:
            case 23:
                objArr[0] = RequestTimeoutException.H("\u00124\u00171\u000f\u0010:/\u0014");
                i3 = a;
                break;
            case 21:
                objArr[0] = MethodGeneratorConfig.H("p%73'd'\u00110,\"\u0012738%=<9");
                i3 = a;
                break;
            case 22:
                objArr[0] = RequestTimeoutException.H("\u0017.\u0014\"\u0014\u00170/Q");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 8:
            case 9:
            case 11:
            case 13:
            case 15:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            default:
                objArr[1] = MethodGeneratorConfig.H("8:4x%#8:>1~*.=,a52/#!041/;\u0012#");
                i4 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = RequestTimeoutException.H("4\u0010\u001a>5\u0005");
                i4 = a;
                break;
            case 3:
                objArr[1] = MethodGeneratorConfig.H("\u001c) 82/9\u0012&#2");
                i4 = a;
                break;
            case 5:
                objArr[1] = RequestTimeoutException.H("\u000b)\u001f6\u0014)\b$\u0004-6;\u0005");
                i4 = a;
                break;
            case 7:
                objArr[1] = MethodGeneratorConfig.H("\u001c!682/9\u0012&#2");
                i4 = a;
                break;
            case 10:
                objArr[1] = RequestTimeoutException.H("\u00180/\u0014");
                i4 = a;
                break;
            case 12:
                objArr[1] = MethodGeneratorConfig.H("8;3+<%");
                i4 = a;
                break;
            case 14:
                objArr[1] = RequestTimeoutException.H(")\u001f=\u001e-\u001f\b\u000e\r:5\u0013");
                i4 = a;
                break;
            case 16:
                objArr[1] = MethodGeneratorConfig.H("957:\u00125!3+6\u001f4+#>9 ( 7$");
                i4 = a;
                break;
            case 18:
                objArr[1] = RequestTimeoutException.H(".\u0010\u001f.4\u0005");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = MethodGeneratorConfig.H("/$%.-2");
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 10:
            case 12:
            case 14:
            case 16:
            case 18:
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = RequestTimeoutException.H("\u00179\u001f2\u00174\r-6;\u0005");
                break;
            case 4:
                objArr[2] = MethodGeneratorConfig.H(">\r\u0014&+#-?0\u0012&#2");
                break;
            case 6:
                objArr[2] = RequestTimeoutException.H("\u00171\t2\u00174\r-6;\u0005");
                break;
            case 8:
            case 9:
                objArr[2] = MethodGeneratorConfig.H("' 7#");
                break;
            case 11:
                objArr[2] = RequestTimeoutException.H("#\u000f\f;$\u0012");
                break;
            case 13:
                objArr[2] = MethodGeneratorConfig.H("\r\u0014-!':\u0013:2*-$");
                break;
            case 15:
                objArr[2] = RequestTimeoutException.H(";5\u001834*\u0011)\u001e\u0012\u0014$\u0014)\u001b\"\u0014\u00170/\u0013");
                break;
            case 17:
                objArr[2] = MethodGeneratorConfig.H("5$ >,2");
                break;
            case 19:
            case 20:
                objArr[2] = RequestTimeoutException.H("\u001d2\u0014424\u00171\u000f\u0010:/\u0014");
                break;
            case 21:
            case 22:
                objArr[2] = MethodGeneratorConfig.H("'\u00110,\"\u0012738%=<9");
                break;
            case 23:
                objArr[2] = RequestTimeoutException.H("3\b\u0018\u00129\u001d?<.\u0003\u000b,$\u0004");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 8:
            case 9:
            case 11:
            case 13:
            case 15:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            default:
                throw new IllegalArgumentException(format);
            case 1:
            case 3:
            case 5:
            case 7:
            case 10:
            case 12:
            case 14:
            case 16:
            case 18:
                throw new IllegalStateException(format);
        }
    }

    @NotNull
    public static /* synthetic */ JComponent minimumSize(@NotNull JComponent $this$minimumSize, int width, int height) {
        if ($this$minimumSize == null) {
            m410enum(2);
        }
        $this$minimumSize.setMinimumSize(new Dimension(width, height));
        if ($this$minimumSize == null) {
            m410enum(3);
        }
        return $this$minimumSize;
    }

    @NotNull
    public static /* synthetic */ JComponent opaque(@NotNull JComponent $this$opaque, boolean z) {
        if ($this$opaque == null) {
            m410enum(17);
        }
        $this$opaque.setOpaque(z);
        if ($this$opaque == null) {
            m410enum(18);
        }
        return $this$opaque;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ void inAllChildren(@NotNull JComponent $this$inAllChildren, @NotNull Function1<? super JComponent, Unit> function1) {
        if ($this$inAllChildren == null) {
            m410enum(21);
        }
        if (function1 == null) {
            m410enum(22);
        }
        function1.invoke($this$inAllChildren);
        JComponent[] components = $this$inAllChildren.getComponents();
        int length = components.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            JComponent jComponent = components[i2];
            if (jComponent != null) {
                inAllChildren(jComponent, function1);
            }
            i2++;
            i = i2;
        }
    }

    public static /* synthetic */ JComponent preferredSize$default(JComponent a, int a2, int a3, int a4, Object obj) {
        if ((a4 & 1) != 0) {
            a2 = a.getPreferredSize().width;
        }
        if ((a4 & 2) != 0) {
            a3 = a.getPreferredSize().height;
        }
        return preferredSize(a, a2, a3);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    public static /* synthetic */ JComponent lockMouseInteractions(@NotNull JComponent $this$lockMouseInteractions) {
        if ($this$lockMouseInteractions == null) {
            m410enum(15);
        }
        MouseListener[] mouseListeners = $this$lockMouseInteractions.getMouseListeners();
        int length = mouseListeners.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            MouseListener mouseListener = mouseListeners[i2];
            i2++;
            $this$lockMouseInteractions.removeMouseListener(mouseListener);
            i = i2;
        }
        if ($this$lockMouseInteractions == null) {
            m410enum(16);
        }
        return $this$lockMouseInteractions;
    }

    @NotNull
    public static /* synthetic */ JComponent maximumSize(@NotNull JComponent $this$maximumSize, int width, int height) {
        if ($this$maximumSize == null) {
            m410enum(6);
        }
        $this$maximumSize.setMaximumSize(new Dimension(width, height));
        if ($this$maximumSize == null) {
            m410enum(7);
        }
        return $this$maximumSize;
    }

    public static /* synthetic */ JComponent maximumSize$default(JComponent a, int a2, int a3, int a4, Object obj) {
        if ((a4 & 1) != 0) {
            a2 = a.getMaximumSize().width;
        }
        if ((a4 & 2) != 0) {
            a3 = a.getMaximumSize().height;
        }
        return maximumSize(a, a2, a3);
    }

    @NotNull
    public static /* synthetic */ JComponent removeInsets(@NotNull JComponent $this$removeInsets) {
        if ($this$removeInsets == null) {
            m410enum(13);
        }
        JBInsets.removeFrom($this$removeInsets.getSize(), $this$removeInsets.getInsets());
        if ($this$removeInsets == null) {
            m410enum(14);
        }
        return $this$removeInsets;
    }

    @NotNull
    public static /* synthetic */ JComponent font(@NotNull JComponent $this$font, @NotNull Font font) {
        if ($this$font == null) {
            m410enum(8);
        }
        if (font == null) {
            m410enum(9);
        }
        $this$font.setFont(font);
        if ($this$font == null) {
            m410enum(10);
        }
        return $this$font;
    }

    @NotNull
    public static /* synthetic */ JComponent border(@NotNull JComponent $this$border, @Nullable Border border) {
        if ($this$border == null) {
            m410enum(11);
        }
        $this$border.setBorder(border);
        if ($this$border == null) {
            m410enum(12);
        }
        return $this$border;
    }

    public static /* synthetic */ JComponent minimumSize$default(JComponent a, int a2, int a3, int a4, Object obj) {
        if ((a4 & 1) != 0) {
            a2 = a.getMinimumSize().width;
        }
        if ((a4 & 2) != 0) {
            a3 = a.getMinimumSize().height;
        }
        return minimumSize(a, a2, a3);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isChildFocused(@NotNull JComponent component) {
        if (component == null) {
            m410enum(23);
        }
        if (!component.isFocusOwner()) {
            JComponent[] components = component.getComponents();
            int length = components.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                JComponent jComponent = components[i2];
                if ((jComponent instanceof JComponent) && isChildFocused(jComponent)) {
                    return true;
                }
                i2++;
                i = i2;
            }
            return false;
        }
        return true;
    }

    @NotNull
    public static /* synthetic */ JComponent update(@NotNull JComponent $this$update) {
        if ($this$update == null) {
            m410enum(0);
        }
        $this$update.revalidate();
        $this$update.repaint();
        if ($this$update == null) {
            m410enum(1);
        }
        return $this$update;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Nullable
    public static /* synthetic */ <T> T findComponent(@NotNull KClass<T> kClass, @NotNull Component component) {
        if (kClass == null) {
            m410enum(19);
        }
        if (component == 0) {
            m410enum(20);
        }
        if (kClass.isInstance(component)) {
            return component;
        }
        Component[] components = ((Container) component).getComponents();
        int length = components.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            T t = (T) findComponent(kClass, components[i2]);
            if (t == null) {
                i2++;
                i = i2;
            } else {
                return t;
            }
        }
        return null;
    }

    public static /* synthetic */ <T> T findComponent(JComponent jComponent) {
        return (T) findComponent(Reflection.getOrCreateKotlinClass(Object.class), jComponent);
    }

    @NotNull
    public static /* synthetic */ JComponent preferredSize(@NotNull JComponent $this$preferredSize, int width, int height) {
        if ($this$preferredSize == null) {
            m410enum(4);
        }
        $this$preferredSize.setPreferredSize(new Dimension(width, height));
        if ($this$preferredSize == null) {
            m410enum(5);
        }
        return $this$preferredSize;
    }
}
