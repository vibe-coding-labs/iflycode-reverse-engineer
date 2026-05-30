package com.aicode.util;

import com.aicode.service.editor.RequestResultList;
import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.Type;
import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/* compiled from: ra */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/TypeUtils.class */
public class TypeUtils {

    /* renamed from: int */
    private static final /* synthetic */ Set<String> f734int;

    /* renamed from: long */
    private static final /* synthetic */ Set<String> f736long;

    /* renamed from: super */
    private static final /* synthetic */ Set<String> f737super;

    /* renamed from: enum */
    private static final /* synthetic */ String f745enum = PropertyUtils.H("o4\u001b\u000f:`;g&r\u0016\u000e0`8h$j>h");

    /* renamed from: if */
    private static final /* synthetic */ Set<String> f739if = ImmutableSet.of(RequestResultList.H("g^rW?O@}g\u001cN[jCiR"), PropertyUtils.H("v$q\u001bAxO$h&p?a"), RequestResultList.H("\u001bCsa^*fcFEvf\u001cN[jCiR"), PropertyUtils.H("\u0018h<p?a"));

    /* renamed from: case */
    private static final /* synthetic */ Set<String> f740case = ImmutableSet.of(RequestResultList.H("g^rW?O@}g\u001cRMrOdA"));

    /* renamed from: char */
    private static final /* synthetic */ Set<String> f733char = ImmutableSet.of(PropertyUtils.H("8d1qYU\"h'2\nx%c"), RequestResultList.H("\u0002Adl\u0011p_|F\u000f_oQ|C\\KsP"), PropertyUtils.H("v/omu\u0002H6i|I(s\u0016L\u0012`?y\u001ap<c"), RequestResultList.H("\u0002Adl\u0011p_|F\u000f_oQ|CLCjP"));

    /* renamed from: this */
    private static final /* synthetic */ Set<String> f731this = ImmutableSet.of(PropertyUtils.H("o&f\u0016\u000e#u\"p`T0v"), RequestResultList.H("xlIe\u0018dWH\u007f.z|\\pgfE"), PropertyUtils.H(",p1q\".#i:/(s zy)\u0004D5x|F(~\u0014U$s.r:T0v"), RequestResultList.H("\u0002tQ|\u0001bQrE>A~M9\u001dsAe\\i\u0001[E4\u000bU`\u007fZjB_BWzgS\u007fC}gfE"), PropertyUtils.H("K:z3+2d\u001eLxD%i#T0v"), RequestResultList.H("X|Yy\u0004/\u001cI~#v`S\u007fWHgyz|\\pgfE"), new String[]{PropertyUtils.H("K:z3+2d\u001eLxU9y+T0v"), RequestResultList.H("@;\u001eA<xKmZ?tDrkz|\\pgfE")});

    /* renamed from: for */
    private static final /* synthetic */ Set<String> f738for = ImmutableSet.of(PropertyUtils.H("<`=}`mi#\u0002\u000f\be?u+u3A\"d\rs<t0r"), RequestResultList.H("^ViF}FertW[@jGfA"));

    /* renamed from: true */
    private static final /* synthetic */ Set<String> f730true = Set.of(PropertyUtils.H("8v8b"), RequestResultList.H("g^rW?O@}g\u001cRMrOdA"), PropertyUtils.H("1\u0017W:\">d)wYt>s$k/{=c"), RequestResultList.H("\u0002Adl\u0011hW\u007fD\u000fPl]sJyHkP"), PropertyUtils.H("v/omu\u001a@5k|D2d\u0018c:n8y/{=c"), RequestResultList.H("Ue@p\rMrnU3jjXhG"), PropertyUtils.H("f3s&>\u001bA8fe_\"x\"u"), RequestResultList.H("D(\u0007nEy\u001cqNvMt/R}bI}ysIDptah_hEuA"), PropertyUtils.H("8d1qYL7o,2\u000bw$k"));

    /* renamed from: else */
    private static final /* synthetic */ Map<String, String> f732else = new HashMap();

    /* renamed from: class */
    private static final /* synthetic */ Map<String, String> f729class = new HashMap();

    /* renamed from: byte */
    private static final /* synthetic */ Map<String, String> f744byte = new HashMap();

    /* renamed from: new */
    private static final /* synthetic */ Map<String, String> f735new = new HashMap();

    /* renamed from: break */
    private static final /* synthetic */ Map<String, String> f728break = new HashMap();

    /* renamed from: try */
    private static final /* synthetic */ Map<String, String> f742try = new HashMap();

    /* renamed from: float */
    private static final /* synthetic */ Set<String> f743float = new HashSet();

    /* renamed from: final */
    private static final /* synthetic */ Set<String> f741final = new HashSet();
    public static final /* synthetic */ Set<String> WRAPPER_TYPES = new HashSet(Arrays.asList(Class.class.getCanonicalName(), Boolean.class.getCanonicalName(), Byte.class.getCanonicalName(), Short.class.getCanonicalName(), Character.class.getCanonicalName(), Integer.class.getCanonicalName(), Long.class.getCanonicalName(), Float.class.getCanonicalName(), Double.class.getCanonicalName(), String.class.getCanonicalName()));
    public static final /* synthetic */ Set<String> JAVA_FUTURE_TYPES = Set.of((Object[]) new String[]{RequestResultList.H("YaD|\u0001m^3\u0004\u000eqbQgCcQD}t\u001c[Zl_uP"), PropertyUtils.H("+w;{hd3y`u4o8b>n<|b/Xb4a\"i\"d\u0016B:d\ri:l#c"), RequestResultList.H("CqTp\r/\u001ch_.QrA{_(\u001aE|y\u0011VC\u007fM@qlW[Zl_uP"), PropertyUtils.H("f:5uei5\u007f!4%~)sy)%e8ueN;wb:\u0014M>_1m\"t\u0002L3e\ri:l#c"), RequestResultList.H("mT>\u001b9PlCq\u0001yGsLu@8\u001dyQ5zzC?\f`KxKCMiLqFAL5\u0004DKeQh[wXt;Czh[qZtGgftGoJLKt^"), PropertyUtils.H("zm-6.#u\"p`zc5\u0015T)~7k3>1U\"t9y\u001ax\"m"), RequestResultList.H("@|Y{\u0006h[i^d\u001bxKx\\kY?\u0006a\tXWrFn]\u007fPRL7\u0018mVt[rAKO(\u001eIqh\u0011UCtVDznU[Zl_uP"), PropertyUtils.H("|,l.6y/*xe\u007f.x.o4c\"~xu\u0011o$j\u0001s'wX:\u0005JuM6d7d\u0012D\u0004t%r/{=c"), RequestResultList.H("Oz_x\u0005/\u001c|K3LxKx\\bPtM.FG\\rYW@qD\u000e\tSy#~`WaWDwCSqCyHkP"), PropertyUtils.H("{&fmu\"t?me\u007f!wo.\u0004S>b&+\u0001\u007f\u0005K\u001cn\"r\u001ax\"m"), RequestResultList.H("EaD+VbQrE7H5\u0006vRo]rKo\u0007VMcH\u0010\u0007h]TSnD6k>\tPfh[VC\u007fM@qlW\\LlCh["), PropertyUtils.H("|,l'?2de7yc9o(i<ki5\u0002\u000f\u0018c'k3u\u0013c9l;p+m4t"), RequestResultList.H("zCgBt\u001duZl\u001c~@vI/\u001aRwcK*dt@Tas[kJLKt^"), PropertyUtils.H("v7w}e4b$va{c5 a9n$x94��~5{F4>n\u0002`8w`Ky5\u0018@9`7@?u\u0014U\"d\n\u007f:p>h"), RequestResultList.H("}DmH7^.\u0001y\t~@yFn[bG\u007fWt+n^p^x[yH6\rfgyJvS?bRjnQNZhZkL"), PropertyUtils.H("'{0piex2;.5n%\u007f;k~>\u0018Uu^7f2b\u0004I d\n\u007f:p>h"), RequestResultList.H("Oz_x\u0005/\u001c|K3LxKx\\bPtM.FB\\mBqJlK8\u0004ETxKqDt\rb|mBqJlCh["), PropertyUtils.H("'{0piex2;.5n%\u007f;k~>\u0018Uu_1m\"t\u0002L3e\ri:l#c")});
    public static final /* synthetic */ Map<String, String> TYPE_TO_ARG_MATCHERS = new HashMap();

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("zSsP"), PropertyUtils.H("`%e\f`%c"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("\\pEuA"), PropertyUtils.H("7o2O&v#r"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("CiA"), PropertyUtils.H("*r7P?r"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("tEiR"), PropertyUtils.H("`%e\u0002v?a"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("ItEfA"), PropertyUtils.H("7o2Z\"v0r"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("y@mHkP"), PropertyUtils.H("A8x\u000fs;{=c"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("{BfG"), PropertyUtils.H("`%e\rq0t"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("Pr@tOf["), PropertyUtils.H("\u0016N/C$s\"|0h"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("nWgB\u000f\u007fa\\z\u0001ZSsP"), PropertyUtils.H("`%e\f`%c"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("Ue@p\rMrnU3|pEuA"), PropertyUtils.H("7o2O&v#r"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("xlIe\u0018}BOt.{s[}MbG"), PropertyUtils.H("*r7P?r"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("nWgB\u000f\u007fa\\z\u0001TEiR"), PropertyUtils.H("`%e\u0002v?a"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("Ue@p\rMrnU3itEfA"), PropertyUtils.H("7o2Z\"v0r"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("g^rW?O@}g\u001cY@mHkP"), PropertyUtils.H("A8x\u000fs;{=c"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("\u0002Adl\u0011hW\u007fD\u000fPhSoN{^bG"), PropertyUtils.H("`%e\rq0t"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("xlIe\u0018}BOt.pr@tOf["), PropertyUtils.H("\u0016N/C$s\"|0h"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("g^rW?O@}g\u001cN[jCiR"), PropertyUtils.H("A8x\u0018h<p?a"));
        TYPE_TO_ARG_MATCHERS.put(RequestResultList.H("0\tVs#SeXv\rb|mB|]yHkP"), PropertyUtils.H("x?\u007f"));
        f736long = new HashSet();
        f736long.addAll(TYPE_TO_ARG_MATCHERS.keySet());
        f736long.add(RequestResultList.H("g^rW?O@}g\u001cRMrOdA"));
        f737super = new HashSet();
        f737super.add(PropertyUtils.H("1m$die\u0003I:/\nn<x(u"));
        f737super.add(RequestResultList.H("g^rW?VUzl\u001cn[jOfX"));
        f737super.add(PropertyUtils.H("f:��@uy&l+>4O;q*n/m>t"));
        f737super.add(RequestResultList.H("@;\u001eA<xKmZ?`N\u007flW~[qEiF"));
        f737super.add(PropertyUtils.H("v/omu\u0003U2`|v3b\u0012A;/\u0018h<|0k"));
        f737super.add(RequestResultList.H("g^rW?O@}g\u001cNVk^bX"));
        f737super.add(PropertyUtils.H("8d1qYU\"h'2\u001bL\u0018B"));
        f737super.add(RequestResultList.H("Js{^*CeJM=IFx]y^hG"));
        f737super.add(PropertyUtils.H("K:z3+2d\u001eLxN)v+z%u"));
        f737super.add(RequestResultList.H("xlIe\u0018dWH\u007f.z|\\pgfE"));
        f737super.add(PropertyUtils.H("K:z3++q\u0019GxC$s\"|0h"));
        f737super.add(RequestResultList.H("bMc\u0018bOG'j\u001cQ@\u007fMbG"));
        f737super.add(PropertyUtils.H("!}8x\".\u0002H7\">j w\u001eN1/\u0007s)~4t"));
        f737super.add(RequestResultList.H("b].Zh[wE6FC}\u007fZ*TtBO=BW|AM^nY"));
        f737super.add(PropertyUtils.H("u4vic|)>n1g9}#|{4\u0004Jun7d)cYb3`%I:p=u"));
        f734int = new HashSet();
        f734int.add(RequestResultList.H("@;\u001eA<xKmZ?`N\u007flW~[qEi\u0016"));
        f734int.add(PropertyUtils.H("!}8x\".\u0002H7\"!q5u\u0016MxR?n+x<%"));
        f734int.add(RequestResultList.H("@;\u001eA<xKmZ?`N~pSoNlEu\u0016"));
        f734int.add(PropertyUtils.H("$xz:XT/e>+\u0004\u007f\u001bL3b?u!w\"%"));
        f734int.add(RequestResultList.H("0\tVs#Jp_}\rlrp\u0011oJuEqP"));
        f734int.add(PropertyUtils.H("1\u0017W:\"'q.|Ym7qh\u007f\"|0t"));
        f734int.add(RequestResultList.H("0\tVs#Jp_}\rlrp\u0011vJaybA"));
        f734int.add(PropertyUtils.H("f:��@uy&l+>:A&\"=}\"l4u"));
        f734int.add(RequestResultList.H("rK,\t\u000egyVh\u0018\\BQ0e\\i]aybA"));
        f734int.add(PropertyUtils.H("v/omu\u0003U2`|D5b\u0016Y%\"*o\u0002p\"r"));
        f734int.add(RequestResultList.H("X|Yy\u00046\tNu#|hWbP\u0002teFPJlBhQ"));
        f734int.add(PropertyUtils.H("8-:#i5y\"vhr(biu5e7oe^+xb\u000e\u0002H7/1j7i'R9q.n:p4u"));
        f734int.add(RequestResultList.H("rK,\t\u000efdRa\u0018]LBrlv|[}\thS"));
        f734int.add(PropertyUtils.H("ub$p\"|2r"));
        f732else.put(RequestResultList.H("0\tVs#Jp_}\rb|l^xLlCh["), PropertyUtils.H("r3v<! `,4:le7mU9n o\u0001s5eozm-6.#u\"p`X~)\u0017X(\"3v\u000by\u0004T~=\u001d]\u0002'x/"));
        f732else.put(RequestResultList.H("Ue@p\rTgi^3k}[rP"), PropertyUtils.H("%y!!v*7wco;q`u\u000f}%w$r\u0001s5eozm-6.#u\"p`X~)\u0017X(\"3v\u000by\u0004T~=\u001d]\u0002'x/"));
        f732else.put(RequestResultList.H("nWgB\u000fft[q\u0001TCtA"), PropertyUtils.H("r3v<! `,4:le7mU9n o\u0001s5eozm-6.#u\"p`X~)\u0017X(\"3v\u000by\u0004T~=\u001d]\u0002'x/"));
        f732else.put(RequestResultList.H("\\pU@=uFtC6gfE"), PropertyUtils.H("%$amp.nmu6`\"po^,i.\\&`0\u000f\u000eP\u0013Ru4gbw+\u0003Us0\u0004D\u000b.[\u001c��@\u0007\"g\",{"));
        f732else.put(RequestResultList.H("rK,\t\u000egyVh\u0018_BWzgS\u007fC}gfE"), PropertyUtils.H("b>44!}7wco2x+>X)2e\u001b`;4gbw+\u0003Us0\u0004D\u000b.[\u001c��@\u0007\"g\",{"));
        f732else.put(RequestResultList.H("YaD|\u0001m^3\u0004\u000eQbQgCcQD}tz|\\pgfE"), PropertyUtils.H("8dkk+w;{amx2/:\bs/u8h4t)dD:$h\u001b`;4gbw+\u0003Us0\u0004D\u000b.[\u001c��@\u0007\"g\",{"));
        f732else.put(RequestResultList.H("rK,\t\u000egyVh\u0018_BWzgS\u007fC}ybA"), PropertyUtils.H("oy<a|,l.6y/*xeH3s(I#eozm-6.#u\"p`X~)\u0017X(\"3v\u000by\u0004T~=\u001d]\u0002'x/"));
        f732else.put(RequestResultList.H("Ue@p\rTgi^3~mOrP"), PropertyUtils.H("%y!!v*7wco;q`u\u000f}%w$r\u0001s5eozm-6.#u\"p`X~)\u0017X(\"3v\u000by\u0004T~=\u001d]\u0002'x/"));
        f732else.put(RequestResultList.H("rK,\t\u000egyVh\u0018CBOwo_\\L{OtF"), PropertyUtils.H("r.66'{9y\".7}'2\u0017s.n)cozm-6.#u\"p`X~)\u0017X(\"3v\u000by\u0004T~=\u001d]\u0002'x/"));
        f732else.put(RequestResultList.H("\\pU@=uFtC6ybA"), PropertyUtils.H("oy<a|,l.6y/*xeT e%I#eozm-6.#u\"p`X~)\u0017X(\"3v\u000by\u0004T~=\u001d]\u0002'x/"));
        f732else.put(RequestResultList.H("\u0002Adl\u0011qBxO\u000f@o@iJ|ybA"), PropertyUtils.H("oy<a|,l.6y/*xeH3s(I#eozm-6.#u\"p`X~)\u0017X(\"3v\u000by\u0004T~=\u001d]\u0002'x/"));
        f732else.put(RequestResultList.H("0\tVs#Jp_}\rmznYxKTCtA"), PropertyUtils.H("%y!!v*7wco;q`u\u000f}%w$r\u0001s5eozm-6.#u\"p`X~)\u0017X(\"3v\u000by\u0004T~=\u001d]\u0002'x/"));
        f732else.put(RequestResultList.H("\u0002Adl\u0011qBxO\u000fRr@|VTCtA"), PropertyUtils.H("r3v<! `,4:le7mU9n o\u0001s5eozm-6.#u\"p`X~)\u0017X(\"3v\u000by\u0004T~=\u001d]\u0002'x/"));
        f732else.put(RequestResultList.H("xlIe\u0018dWH\u007f.z|\\pgfE"), PropertyUtils.H("b>44!}7wco2x+>D:$h\u001b`;4gbw+\u0003Us0\u0004D\u000b.[\u001c��@\u0007\"g\",{"));
        f732else.put(RequestResultList.H("xlIe\u0018dWH\u007f.foJ}gfE"), PropertyUtils.H("b>44!}7wco2x+>X)2e\u001b`;4gbw+\u0003Us0\u0004D\u000b.[\u001c��@\u0007\"g\",{"));
        f732else.put(RequestResultList.H("g^rW?VUzl\u001cKJ{^hG"), PropertyUtils.H("r.66'{9y\".7}'2\u0017s.n)cozm-6.#u\"p`X~)\u0017X(\"3v\u000by\u0004T~=\u001d]\u0002'x/"));
        f732else.put(RequestResultList.H("xlIe\u0018dWH\u007f.z|\\pybA"), PropertyUtils.H("oy<a|,l.6y/*xeT e%I#eozm-6.#u\"p`X~)\u0017X(\"3v\u000by\u0004T~=\u001d]\u0002'x/"));
        f732else.put(RequestResultList.H("Ue@p\rTgi^3|lKd^"), PropertyUtils.H("x(mf{&fmu\"t?meO:xo0^\b w\"p4x_\u001c��@\u0007\"g\",{"));
        f732else.put(RequestResultList.H("xlIe\u0018dWH\u007f.foJ}ybA"), PropertyUtils.H("oy<a|,l.6y/*xeH3s(I#eozm-6.#u\"p`X~)\u0017X(\"3v\u000by\u0004T~=\u001d]\u0002'x/"));
        f732else.put(RequestResultList.H("@5\u0005/Rl[\u007fNzKt\u000eAayUwY\u007f\rk@O|RMrOdA"), PropertyUtils.H("\"~<<5nqe z$x.zmu%u8h+e\"th[\u0014_B\u00145j3b?4g7k>\u0002k\bC\u001cJ%z\u0012C\")wJ\u000fUo/"));
        f729class.put(RequestResultList.H("0\tVs#Jp_}\rb|l^xLlCh["), PropertyUtils.H("!}8x\".\u0002H7\"\u001el4dYO0)wJ\u000fUo/"));
        f729class.put(RequestResultList.H("Ue@p\rTgi^3k}[rP"), PropertyUtils.H("r.66'{9y\".7}'2\r\u007f#q#u\u000by\u007f/k>~k*j/7y/\u001fMu@;v3>\u0018F~=\u001d]\u0002'x/"));
        f729class.put(RequestResultList.H("nWgB\u000fft[q\u0001TCtA"), PropertyUtils.H("%y!!v*7wco;q`u\u0002f9}8Z$i2-\u0013I\\\u001e\u0004>~k*j/7y/\u001fMu@;v3>\u0018F~=\u001d]\u0002'x/"));
        f729class.put(RequestResultList.H("\\pU@=uFtC6gfE"), PropertyUtils.H("%$amp.nmu6`\"po^,i.\\&`0\u000f\u000eP\u0013Ru4gbw+\u0003Us0\u0004D\u000b.[\u001c��@\u0007\"g\",{"));
        f729class.put(RequestResultList.H("rK,\t\u000egyVh\u0018_BWzgS\u007fC}gfE"), PropertyUtils.H("!}{{)u=}oc9s*?\u0013bi>\u001aa&=u4gbw+\u0003Us0\u0004D\u000b.[\u001c��@\u0007\"g\",{"));
        f729class.put(RequestResultList.H("YaD|\u0001m^3\u0004\u000eQbQgCcQD}tz|\\pgfE"), PropertyUtils.H("8dkk+w;{amx2/:\bs/u8h4t)dD:$h\u001b`;4gbw+\u0003Us0\u0004D\u000b.[\u001c��@\u0007\"g\",{"));
        f729class.put(RequestResultList.H("rK,\t\u000egyVh\u0018_BWzgS\u007fC}ybA"), PropertyUtils.H("x(morm-\":>h(zcN4t\"Ci/k>~k*j/7y/\u001fMu@;v3>\u0018F~=\u001d]\u0002'x/"));
        f729class.put(RequestResultList.H("Ue@p\rTgi^3~mOrP"), PropertyUtils.H("r.66'{9y\".7}'2\r\u007f#q#u\u000by\u007f/k>~k*j/7y/\u001fMu@;v3>\u0018F~=\u001d]\u0002'x/"));
        f729class.put(RequestResultList.H("rK,\t\u000egyVh\u0018CBOwo_\\L{OtF"), PropertyUtils.H("#\u007f88f:5uei5\u007f!4\u0010t$dc)k>~k*j/7y/\u001fMu@;v3>\u0018F~=\u001d]\u0002'x/"));
        f729class.put(RequestResultList.H("\\pU@=uFtC6ybA"), PropertyUtils.H("v/omu\u0003U2`|V\"dYO0)wJ\u000fUo/"));
        f729class.put(RequestResultList.H("\u0002Adl\u0011qBxO\u000f@o@iJ|ybA"), PropertyUtils.H("x(morm-\":>h(zcN4t\"Ci/k>~k*j/7y/\u001fMu@;v3>\u0018F~=\u001d]\u0002'x/"));
        f729class.put(RequestResultList.H("0\tVs#Jp_}\rmznYxKTCtA"), PropertyUtils.H("r.66'{9y\".7}'2\r\u007f#q#u\u000by\u007f/k>~k*j/7y/\u001fMu@;v3>\u0018F~=\u001d]\u0002'x/"));
        f729class.put(RequestResultList.H("\u0002Adl\u0011qBxO\u000fRr@|VTCtA"), PropertyUtils.H("%$amp.nmu6`\"poW?h'h\u000by\u007f/k>~k*j/7y/\u001fMu@;v3>\u0018F~=\u001d]\u0002'x/"));
        f729class.put(RequestResultList.H("xlIe\u0018dWH\u007f.z|\\pgfE"), PropertyUtils.H("%$amp.nmu6`\"po^,i.\\&`0\u000f\u000eP\u0013Ru4gbw+\u0003Us0\u0004D\u000b.[\u001c��@\u0007\"g\",{"));
        f729class.put(RequestResultList.H("xlIe\u0018dWH\u007f.foJ}gfE"), PropertyUtils.H("!}{{)u=}oc9s*?\u0013bi>\u001aa&=u4gbw+\u0003Us0\u0004D\u000b.[\u001c��@\u0007\"g\",{"));
        f729class.put(RequestResultList.H("g^rW?VUzl\u001cKJ{^hG"), PropertyUtils.H("#\u007f88f:5uei5\u007f!4\u0010t$dc)k>~k*j/7y/\u001fMu@;v3>\u0018F~=\u001d]\u0002'x/"));
        f729class.put(RequestResultList.H("xlIe\u0018dWH\u007f.z|\\pybA"), PropertyUtils.H("x(morm-\":>h(zcR'b/Ci/k>~k*j/7y/\u001fMu@;v3>\u0018F~=\u001d]\u0002'x/"));
        f729class.put(RequestResultList.H("Ue@p\rTgi^3|lKd^"), PropertyUtils.H("r$amp'g&>y/>lxR?}-r0e^\b w\"p4x_\u001c��@\u0007\"g\",{"));
        f729class.put(RequestResultList.H("xlIe\u0018dWH\u007f.foJ}ybA"), PropertyUtils.H("x(morm-\":>h(zcN4t\"Ci/k>~k*j/7y/\u001fMu@;v3>\u0018F~=\u001d]\u0002'x/"));
        f729class.put(RequestResultList.H("@5\u0005/Rl[\u007fNzKt\u000eAayUwY\u007f\rk@O|RMrOdA"), PropertyUtils.H("o4.:*p(t,x'?!q\u007f/=s9oeV\u001dVBu\u0006@)\u007f7J%z\u0012C\")wJ\u000fUo/"));
        f744byte.putAll(f732else);
        f735new.putAll(f732else);
        f728break.putAll(f729class);
        f742try.putAll(f729class);
        for (String str : JAVA_FUTURE_TYPES) {
            f744byte.put(str, RequestResultList.H("@|Y{\u0006h[i^d\u001bxKx\\kY?\u0006a\t^@zUwLdCsO?.tGu@x\u0001{E7\u0018LwyZ`pdWTae\u001a!yYf9\u001c"));
            f728break.put(str, PropertyUtils.H("s*j-5>h?m2(.x.o=ji57:\bs,f!\u007f2p%|i\u001d\"t#s.2-va+\u001aD/i6C2d\u0002R3)wJ\u000fUo/"));
            f735new.put(str, RequestResultList.H("\u0013NkK\u000b"));
            f742try.put(str, PropertyUtils.H(" \u0018X\u001d8"));
        }
        f743float.add(RequestResultList.H("s@lCaL"));
        f743float.add(PropertyUtils.H("N9u\"z7X=j"));
        f743float.add(RequestResultList.H("oKnA"));
        f743float.add(PropertyUtils.H("0h%}\"p+c"));
        f743float.add(RequestResultList.H("gWiltKtF"));
        f743float.add(PropertyUtils.H("\u007f\"v?c"));
        f741final.add(RequestResultList.H("o@z\u0001r_4\u0001T<gJt_eFS=aBt\u0001LOtA"));
        f741final.add(PropertyUtils.H("c)0.<t%u:7f.\u0006H/i +&`\u001e\u000e\u0017g?y<X=j"));
        f741final.add(RequestResultList.H("L(\u000f/Yu\\t[6@/\u0018IfhM*WaJ\u000fRfFx]]Kd]"));
        f741final.add(PropertyUtils.H("\u007f~<yj#o\"h`sy+\u001fU>~|d7yYb3g$n+X=j"));
        f741final.add(RequestResultList.H("~Q=FkFn[i\u0001r_*\u0001Tw\u007f\u0011eFx\rcvf]oJ]Kd]"));
        f741final.add(PropertyUtils.H("c)0.<t%u:7f.\u0006H/i +&`\u001e\u000e\u0012h8},u4b"));
        f741final.add(RequestResultList.H("McDt\u0002t]iF3EmZ3\u001cE`#^t_?gH`p^|VVKjP"));
        f741final.add(PropertyUtils.H("41sev4x$nh{2`e/2rx`;u`]e(\u0006M:u\u001cd*u0E8d9}:p>h"));
        f741final.add(RequestResultList.H("\u007fPv\r0\u001doZt\u001cwZhC.\rR<lOm\u0018EFRgTWp_tKsP"));
        f741final.add(PropertyUtils.H("4%gxk>r'm\"1\u0003Q2x7wiq\u0007IxU\"q+v$r"));
        f741final.add(RequestResultList.H("kDv\rKfn[i\u0001LOtA"));
        f741final.add(PropertyUtils.H("4~5+-e\u0019I\"/\ty(v#c"));
        f741final.add(RequestResultList.H("E(\u000f\u000exxQmB?aDuo@xltKtF"));
        f741final.add(PropertyUtils.H("4\u0004Fuf'k.dYc:`8o\u001cl=c"));
        f741final.add(RequestResultList.H("r]\u007f\u00040\u001dN{y\u0011B_inDgh]y`jNbG"));
        f741final.add(PropertyUtils.H("4~5+-e\u0019I\"/\u0002{ v#c"));
        f741final.add(RequestResultList.H("kDv\rKfn[i\u0001J_kP"));
        f741final.add(PropertyUtils.H("c biz\u0002N?ue](m4t"));
        f741final.add(RequestResultList.H("5\u001aG<gJj_e\r`utWoltKtF"));
        f741final.add(PropertyUtils.H("s<~\"6\u0019B0e&jiY\u0019J3b?Q!z:u"));
        f741final.add(RequestResultList.H("O`j\u0011iYrHHgo\u001c^Nh^hG"));
        f741final.add(PropertyUtils.H("4~5+*\u007f\u0014K?u$2\u0003v2m"));
        f741final.add(RequestResultList.H("PvQ?NNpk[i@6ywL"));
        f741final.add(PropertyUtils.H("o$feh+jx5\u0011\u000f:b<j3q\u0003I9o82\u001a|\"r"));
        f741final.add(RequestResultList.H("\u007fPv\r.\rrGnU3NvD5\u001cAfdPjE?aDuo@x|mCsP"));
        f741final.add(PropertyUtils.H("~5w\"/2s\"o,2/wb4\u0002@/e=k4>6F\"d9O;p%c"));
        f741final.add(RequestResultList.H("McDt\u001cd@t\\z\u0001yD4\u0007TsyVkXb\rcvf]oJLOtA"));
        f741final.add(PropertyUtils.H("(bku#e%u%{`xb5\u0019U:x;j)cYa0u.n\u001a|\"r"));
        f741final.add(RequestResultList.H("\u007fPv\r.\rrGnU3NvD5\u001cAfdPjE?aDuo@xltKtF"));
        f741final.add(PropertyUtils.H("~5w\"/2s\"o,2/wb4\u0002@/e=k4>6F\"d9_\"x\"u"));
        f741final.add(RequestResultList.H("FbE?W?\u001bu]g\u001c|AvE.\tT{bQw\u0018SFG|rWPJlBhQ"));
        f741final.add(PropertyUtils.H(")c >x>$t8fe} wc/\u0017U2c<viQ\u0011T3s\u0006y:q>b"));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isCollectionType(Type a) {
        return Stream.of((Object[]) new String[]{RequestResultList.H("nWgB\u000fft[q\u0001TCtA"), PropertyUtils.H("f:��@uy&l+>;I8j.x\u0002p\"r"), RequestResultList.H("\u0002Adl\u0011qBxO\u000fRr@|VTCtA")}).anyMatch(a2 -> {
            return a.getCanonicalName().startsWith(a2);
        }) || a.isArray();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isNoMockStaticType(String a, Type a2) {
        if (a2 != null) {
            if (!a2.getCanonicalName().equals(PropertyUtils.H("K:z3++q\u0019GxC$s\"|0h")) && !a2.getCanonicalName().equals(RequestResultList.H("Pr@tOf["))) {
                Stream<String> stream = f737super.stream();
                String canonicalName = a2.getCanonicalName();
                Objects.requireNonNull(canonicalName);
                if (stream.anyMatch(canonicalName::startsWith)) {
                    return true;
                }
            } else {
                return true;
            }
        }
        Stream<String> stream2 = f734int.stream();
        Objects.requireNonNull(a);
        return stream2.anyMatch(a::startsWith);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isMockable(Type a) {
        return (a.isPrimitive() || WRAPPER_TYPES.contains(a.getCanonicalName()) || a.isArray() || a.isEnum()) ? false : true;
    }

    public static /* synthetic */ boolean isStringType(String a) {
        return f739if.contains(a);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isBasicType(Type a) {
        return a == null || a.isEnum() || f736long.contains(a.getCanonicalName()) || RequestResultList.H("nEnQ").equalsIgnoreCase(a.getCanonicalName()) || PropertyUtils.H("8d1qYL7o,28v8b").equalsIgnoreCase(a.getCanonicalName());
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public static /* synthetic */ boolean hasValidEmptyConstructor(Type a) {
        if (a.isInterface() || a.isAbstract()) {
            return false;
        }
        if (a.isHasDefaultConstructor()) {
            return true;
        }
        for (Method method : a.findConstructors()) {
            if (method.isAccessible() && method.getMethodParams().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isArrayType(Type a) {
        return Stream.of((Object[]) new String[]{RequestResultList.H("0\tVs#Jp_}\rb|l^xLlCh["), PropertyUtils.H("8d1qYU\"h'2\u0002p\"r"), RequestResultList.H("Ue@p\rTgi^3k}[rP"), PropertyUtils.H("f:��@uy&l+>;I8j.x\u0002p\"r"), RequestResultList.H("\u0002Adl\u0011qBxO\u000fRr@|VTCtA"), PropertyUtils.H("o&f\u0016\u000e#u\"p`J4r"), RequestResultList.H("Ue@p\rTgi^3~mOrP"), PropertyUtils.H("+w;{hd3y`u4o8b>n<|b/Xb4b1p5b\u0012N\"I*o&T0v")}).anyMatch(a2 -> {
            return a.getCanonicalName().startsWith(a2);
        }) || a.isArray();
    }

    public static /* synthetic */ Boolean isMap(String a) {
        Stream<String> stream = f731this.stream();
        Objects.requireNonNull(a);
        return Boolean.valueOf(stream.anyMatch(a::startsWith));
    }

    public static /* synthetic */ boolean isIgnore(String a) {
        return f743float.contains(a);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isNoImportType(Type a) {
        return a.isInterface() || isLanguageBaseClass(a.getCanonicalName()) || isBasicType(a) || f744byte.keySet().contains(a.getCanonicalName());
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isDateType(Type a) {
        return a != null && f733char.contains(a.getCanonicalName());
    }

    public static /* synthetic */ boolean isTestAnnotation(String a) {
        return f741final.contains(a);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isLanguageBaseClass(String a) {
        return a != null && f730true.contains(a);
    }

    public static /* synthetic */ boolean isBasicType(String a) {
        return f736long.contains(a);
    }

    public static /* synthetic */ Boolean isJSONObject(String a) {
        return Boolean.valueOf(StringUtils.startWith(a, PropertyUtils.H("o4\u001b\u000f:`;g&r\u0016\u000e0`8h$j>h")));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isInArray(Type a) {
        return a != null && a.isArray();
    }
}
