package net.dontdrinkandroot.gitki.wicket.page

import net.dontdrinkandroot.gitki.wicket.component.BrandLink
import net.dontdrinkandroot.gitki.wicket.component.feedback.FlashMessagePanel
import net.dontdrinkandroot.gitki.wicket.component.item.AdminDropdownItem
import net.dontdrinkandroot.gitki.wicket.component.item.HistoryPageItem
import net.dontdrinkandroot.gitki.wicket.component.item.SignInPageLinkItem
import net.dontdrinkandroot.gitki.wicket.component.item.UserDropdownItem
import net.dontdrinkandroot.gitki.wicket.getCurrentUser
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.behavior.OutputMarkupIdBehavior
import net.dontdrinkandroot.wicket.behavior.cssClass
import net.dontdrinkandroot.wicket.bootstrap.behavior.ModalRequestBehavior
import net.dontdrinkandroot.wicket.bootstrap.component.navbar.addNavbar
import net.dontdrinkandroot.wicket.bootstrap.component.navbar.navbarNav
import net.dontdrinkandroot.wicket.bootstrap.css.BackgroundColor
import net.dontdrinkandroot.wicket.bootstrap.css.NavbarPosition
import net.dontdrinkandroot.wicket.bootstrap.css.Spacing
import net.dontdrinkandroot.wicket.markup.html.markupContainer
import net.dontdrinkandroot.wicket.util.NonStatelessPrintingVisitor
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.util.visit.Visits

abstract class DecoratorPage<T> : ScaffoldPage<T> {

    constructor(parameters: PageParameters) : super(parameters)

    constructor(model: IModel<T>) : super(model)

    override fun onInitialize() {
        super.onInitialize()
        addNavbar(
            "navbar",
            positionModel = Model(NavbarPosition.FIXED_TOP),
            createBrandHandler = { id -> BrandLink(id) },
            behaviors = arrayOf(CssClassAppender(BackgroundColor.WHITE))
        ) {
            navbarNav(cssClass(Spacing.MARGIN_END_AUTO)) {
                populateNavbarLeftItems(this)
            }
            navbarNav {
                populateNavbarRightItems(this)
            }
        }
        add(FlashMessagePanel("flashMessages"))
        createModal()
    }

    override fun onBeforeRender() {
        super.onBeforeRender()
        if (null == getCurrentUser()) {
            // TODO: Remove
            Visits.visit(this, NonStatelessPrintingVisitor())
        }
    }

    private fun populateNavbarLeftItems(itemView: RepeatingView) {
        itemView.add(HistoryPageItem(itemView.newChildId()))
    }

    private fun populateNavbarRightItems(itemView: RepeatingView) {
        itemView.add(AdminDropdownItem(itemView.newChildId()))
        itemView.add(UserDropdownItem(itemView.newChildId()))
        itemView.add(SignInPageLinkItem(itemView.newChildId()))
    }

    private fun createModal() {
        val modalId = "modal"
        add(ModalRequestBehavior(modalId))
        markupContainer(modalId, OutputMarkupIdBehavior())
    }
}