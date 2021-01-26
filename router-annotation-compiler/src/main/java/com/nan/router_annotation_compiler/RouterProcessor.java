package com.nan.router_annotation_compiler;

import com.google.auto.service.AutoService;
import com.nan.router_annotation.RoutePath;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Messager mMessager;
    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
        mElementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 扫描RoutePath
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(RoutePath.class);
        Map<String, String> routeMap = new HashMap<>();
        for (Element element : elements) {
            if (!(element instanceof TypeElement)) {
                continue;
            }
            TypeElement typeElement = (TypeElement) element;
            // Activity类名
            String activityName = typeElement.getQualifiedName().toString();
            // 路径
            String routePath = typeElement.getAnnotation(RoutePath.class).value();
            routeMap.put(routePath, activityName);
        }

        // 生成类
        if (routeMap.size() > 0) {
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder("RouterInjector_" + System.currentTimeMillis())
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addSuperinterface(ClassName.get("com.nan.wechat.router", "IRouterInjector"));

            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("injectActivity")
                    .addModifiers(Modifier.PUBLIC);

            Iterator<String> iterator = routeMap.keySet().iterator();
            while (iterator.hasNext()) {
                String routePath = iterator.next();
                String activityName = routeMap.get(routePath);
                methodBuilder.addStatement("$T.getInstance().addActivity(\"$L\", $L.class)", ClassName.get("com.nan.wechat.router", "Router"), routePath, activityName);
            }

            classBuilder.addMethod(methodBuilder.build());

            try {
                JavaFile.builder("com.nan.wechat.router.compile", classBuilder.build())
                        .addFileComment("自动生成的代码,请不要改动")
                        .build()
                        .writeTo(mFiler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // mMessager.printMessage(Diagnostic.Kind.ERROR, "测试");

        return false;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(RoutePath.class);
        return annotations;
    }
}
