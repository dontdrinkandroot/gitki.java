package net.dontdrinkandroot.gitki.test

import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.wicket.GitkiWebApplication
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.gitki.wicket.page.SignInPage
import org.apache.commons.lang3.reflect.ConstructorUtils
import org.apache.wicket.Page
import org.apache.wicket.authorization.UnauthorizedInstantiationException
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.util.tester.WicketTester
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import java.lang.reflect.InvocationTargetException

abstract class AbstractWicketTest : AbstractIntegrationTest() {

    @Autowired
    protected lateinit var wicketApplication: GitkiWebApplication

    lateinit var wicketTester: WicketTester

    @BeforeEach
    fun before() {
        wicketTester = WicketTester(wicketApplication)
    }

    protected fun assertLoginRequired(pageClass: Class<out Page?>, parameters: PageParameters? = PageParameters()) {
        wicketTester.startPage(pageClass, parameters)
        wicketTester.assertRenderedPage(SignInPage::class.java)
    }

    protected fun <P : Page?> assertPageAccessible(pageClass: Class<P>, user: User?, vararg parameters: Any?): P {
        getGitkiSession().invalidate()
        if (null != user) {
            getGitkiSession().signIn(user)
        }
        val page = startPage(pageClass, parameters)
        wicketTester.assertRenderedPage(pageClass)
        return page
    }

    protected fun assertPageInaccessible(pageClass: Class<out Page>, user: User?, vararg parameters: Any?) {
        getGitkiSession().invalidate()
        if (null != user) {
            getGitkiSession().signIn(user)
        }
        try {
            startPage(pageClass, parameters)
            Assertions.fail<Any>("UnauthorizedInstantiationException expected")
        } catch (e: UnauthorizedInstantiationException) {
            /* Expected */
        }
    }

    protected fun <C : Page?> startPage(pageClass: Class<C>, parameters: Array<out Any?>): C {
        if (parameters.isEmpty()) {
            return wicketTester.startPage(pageClass)
        }
        return if (1 == parameters.size && parameters[0] is PageParameters) {
            wicketTester.startPage(pageClass, parameters[0] as PageParameters?)
        } else try {
            val page = ConstructorUtils.invokeConstructor(pageClass, *parameters)
            wicketTester.startPage(page)
        } catch (e: NoSuchMethodException) {
            if (e is InvocationTargetException) {
                if (e.targetException is UnauthorizedInstantiationException) {
                    throw (e.targetException as UnauthorizedInstantiationException)
                }
            }
            throw RuntimeException(e)
        } catch (e: InstantiationException) {
            if (e is InvocationTargetException) {
                if (e.targetException is UnauthorizedInstantiationException) {
                    throw (e.targetException as UnauthorizedInstantiationException)
                }
            }
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            if (e.targetException is UnauthorizedInstantiationException) {
                throw (e.targetException as UnauthorizedInstantiationException)
            }
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            if (e is InvocationTargetException) {
                if (e.targetException is UnauthorizedInstantiationException) {
                    throw (e.targetException as UnauthorizedInstantiationException)
                }
            }
            throw RuntimeException(e)
        }
    }
}