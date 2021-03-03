package net.dontdrinkandroot.gitki.wicket.page

import net.dontdrinkandroot.gitki.wicket.component.BrandLink
import net.dontdrinkandroot.gitki.wicket.component.feedback.FlashMessagePanel
import net.dontdrinkandroot.gitki.wicket.component.item.AdminDropdownItem
import net.dontdrinkandroot.gitki.wicket.component.item.HistoryPageItem
import net.dontdrinkandroot.gitki.wicket.component.item.SignInPageLinkItem
import net.dontdrinkandroot.gitki.wicket.component.item.UserDropdownItem
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.behavior.OutputMarkupIdBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.ModalRequestBehavior
import net.dontdrinkandroot.wicket.bootstrap.component.navbar.Navbar
import net.dontdrinkandroot.wicket.bootstrap.component.navbar.RepeatingNavbarNav
import net.dontdrinkandroot.wicket.bootstrap.css.BackgroundColor
import net.dontdrinkandroot.wicket.bootstrap.css.NavbarPosition
import net.dontdrinkandroot.wicket.bootstrap.css.Spacing
import net.dontdrinkandroot.wicket.markup.html.WebMarkupContainer
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
        add(Navbar(
            "navbar",
            positionModel = Model(NavbarPosition.FIXED_TOP),
            behaviors = listOf(CssClassAppender(BackgroundColor.WHITE)),
            createBrandHandler = { id -> BrandLink(id) }
        ) { collapseItemView ->

            collapseItemView.add(RepeatingNavbarNav<Void>(
                collapseItemView.newChildId(),
                behaviors = listOf(CssClassAppender(Spacing.MARGIN_END_AUTO))
            ) { itemView -> populateNavbarLeftItems(itemView) })

            collapseItemView.add(RepeatingNavbarNav<Void>(collapseItemView.newChildId()) { itemView ->
                populateNavbarRightItems(itemView)
            })
        })
        add(FlashMessagePanel("flashMessages"))
        createModal()
    }

    override fun onBeforeRender() {
        super.onBeforeRender()
        if (null == net.dontdrinkandroot.gitki.wicket.getGitkiSession().user) {
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
        add(WebMarkupContainer<Void>(modalId, behaviors = listOf(OutputMarkupIdBehavior())))
    }
}