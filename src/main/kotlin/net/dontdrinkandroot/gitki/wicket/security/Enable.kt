package net.dontdrinkandroot.gitki.wicket.security

import net.dontdrinkandroot.gitki.model.Role
import java.lang.annotation.Inherited

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FILE, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@MustBeDocumented
@Inherited
annotation class Enable(val value: Role)