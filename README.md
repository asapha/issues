Run `./gradlew runR8`  

Ok with compose multiplatform 1.7.3.  

With 1.8.1:

```kotlin
Caused by: java.lang.ClassNotFoundException: androidx.compose.foundation.layout.Arrangement$Vertical
	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:641)
	at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:188)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:528)
	... 126 more
Caused by: java.lang.ClassNotFoundException: androidx.compose.foundation.layout.Arrangement$Vertical
```

With 1.8.1. + `-keep class ** { *; }`:

```kotlin
Exception in thread "main" java.lang.VerifyError: Bad invokespecial instruction: interface method reference is in an indirect superinterface.
Exception Details:
  Location:
    androidx/compose/ui/layout/MeasureScope.access$roundToPx-0680j_4$jd(Landroidx/compose/ui/layout/MeasureScope;F)I @2: invokespecial
  Reason:
    Error exists in the bytecode
  Bytecode:
    0000000: 2a23 b700 59ac                         

	at androidx.compose.ui.node.NodeChain.<init>(SourceFile:35)
	at androidx.compose.ui.node.LayoutNode.<init>(SourceFile:866)
	at androidx.compose.ui.node.LayoutNode.<init>(SourceFile:77)
	at androidx.compose.ui.node.RootNodeOwner$OwnerImpl.<init>(SourceFile:343)
	at androidx.compose.ui.node.RootNodeOwner.<init>(SourceFile:163)
	at androidx.compose.ui.node.RootNodeOwner.<init>(SourceFile)
	at androidx.compose.ui.scene.CanvasLayersComposeSceneImpl.<init>(SourceFile:111)
	at androidx.compose.ui.scene.CanvasLayersComposeSceneImpl.<init>(SourceFile)
	at androidx.compose.ui.scene.CanvasLayersComposeScene_skikoKt.CanvasLayersComposeScene-3tKcejY(SourceFile:91)
	at androidx.compose.ui.scene.CanvasLayersComposeScene_skikoKt.CanvasLayersComposeScene-3tKcejY$default(SourceFile:82)
	at androidx.compose.ui.scene.ComposeContainer.createComposeScene(SourceFile:368)
	at androidx.compose.ui.scene.ComposeContainer.access$createComposeScene(SourceFile:87)
	at androidx.compose.ui.scene.ComposeContainer$mediator$3.invoke(SourceFile:145)
	at androidx.compose.ui.scene.ComposeContainer$mediator$3.invoke(SourceFile:145)
	at androidx.compose.ui.scene.ComposeSceneMediator.scene_delegate$lambda$2(SourceFile:315)
	at kotlin.SynchronizedLazyImpl.getValue(SourceFile:83)
	at androidx.compose.ui.scene.ComposeSceneMediator.getScene(SourceFile:315)
	at androidx.compose.ui.scene.ComposeSceneMediator.setCompositionLocalContext(SourceFile:319)
	at androidx.compose.ui.scene.ComposeContainer.setCompositionLocalContext(SourceFile:173)
	at androidx.compose.ui.awt.ComposeWindowPanel.setCompositionLocalContext(SourceFile:75)
	at androidx.compose.ui.awt.ComposeWindow.setCompositionLocalContext(SourceFile:115)
	at androidx.compose.ui.window.Window_desktopKt.Window$lambda$36$lambda$35(SourceFile:606)
	at androidx.compose.ui.window.AwtWindow_desktopKt.AwtWindow$lambda$5$lambda$4(SourceFile:70)
	at androidx.compose.runtime.DisposableEffectImpl.onRemembered(SourceFile:83)
	at androidx.compose.runtime.internal.RememberEventDispatcher.dispatchRememberList(SourceFile:182)
	at androidx.compose.runtime.internal.RememberEventDispatcher.dispatchRememberObservers(SourceFile:174)
	at androidx.compose.runtime.CompositionImpl.applyChangesInLocked(SourceFile:1044)
	at androidx.compose.runtime.CompositionImpl.applyChanges(SourceFile:1067)
	at androidx.compose.runtime.Recomposer.composeInitial$runtime(SourceFile:1159)
	at androidx.compose.runtime.CompositionImpl.composeInitial(SourceFile:677)
	at androidx.compose.runtime.CompositionImpl.setContent(SourceFile:616)
	at androidx.compose.ui.window.Application_desktopKt$awaitApplication$2$1$2.invokeSuspend(SourceFile:221)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(SourceFile:33)
	at kotlinx.coroutines.DispatchedTask.run(SourceFile:104)
	at java.desktop/java.awt.event.InvocationEvent.dispatch(InvocationEvent.java:318)
	at java.desktop/java.awt.EventQueue.dispatchEventImpl(EventQueue.java:773)
	at java.desktop/java.awt.EventQueue$4.run(EventQueue.java:720)
	at java.desktop/java.awt.EventQueue$4.run(EventQueue.java:714)
	at java.base/java.security.AccessController.doPrivileged(AccessController.java:400)
	at java.base/java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:87)
	at java.desktop/java.awt.EventQueue.dispatchEvent(EventQueue.java:742)
	at java.desktop/java.awt.EventDispatchThread.pumpOneEventForFilters(EventDispatchThread.java:203)
	at java.desktop/java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:124)
	at java.desktop/java.awt.EventDispatchThread.pumpEventsForHierarchy(EventDispatchThread.java:113)
	at java.desktop/java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:109)
	at java.desktop/java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:101)
	at java.desktop/java.awt.EventDispatchThread.run(EventDispatchThread.java:90)

```