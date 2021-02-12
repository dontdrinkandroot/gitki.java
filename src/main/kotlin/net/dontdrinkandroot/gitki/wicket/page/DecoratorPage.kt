package net.dontdrinkandroot.gitki.wicket.page

import net.dontdrinkandroot.gitki.wicket.component.BrandLink
import net.dontdrinkandroot.gitki.wicket.component.feedback.FlashMessagePanel
import net.dontdrinkandroot.gitki.wicket.component.item.AdminDropdownItem
import net.dontdrinkandroot.gitki.wicket.component.item.HistoryPageItem
import net.dontdrinkandroot.gitki.wicket.component.item.SignInPageLinkItem
import net.dontdrinkandroot.gitki.wicket.component.item.UserDropdownItem
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.behavior.ModalRequestBehavior
import net.dontdrinkandroot.wicket.bootstrap.component.navbar.Navbar
import net.dontdrinkandroot.wicket.bootstrap.component.navbar.RepeatingNavbarNav
import net.dontdrinkandroot.wicket.bootstrap.css.NavbarPosition
import net.dontdrinkandroot.wicket.bootstrap.css.Spacing
import net.dontdrinkandroot.wicket.util.NonStatelessPrintingVisitor
import org.apache.wicket.Component
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.util.visit.Visits

abstract class DecoratorPage<T> : ScaffoldPage<T> {

    constructor(parameters: PageParameters) : super(parameters)

    constructor(model: IModel<T>) : super(model)

    override fun onInitialize() {
        super.onInitialize()
        val navbar: Navbar = object : Navbar("navbar") {
            override fun createBrand(id: String): Component = BrandLink(id)

            override fun populateCollapseItems(collapseItemView: RepeatingView) {
                super.populateCollapseItems(collapseItemView)
                val navbarLeft: RepeatingNavbarNav<Void> =
                    object : RepeatingNavbarNav<Void>(collapseItemView.newChildId()) {
                        override fun populateItems(itemView: RepeatingView) {
                            super.populateItems(itemView)
                            populateNavbarLeftItems(itemView)
                        }
                    }
                navbarLeft.add(CssClassAppender(Spacing(Spacing.Property.MARGIN, Spacing.Size.AUTO, Spacing.Side.END)))
                collapseItemView.add(navbarLeft)
                val navbarRight: RepeatingNavbarNav<Void> =
                    object : RepeatingNavbarNav<Void>(collapseItemView.newChildId()) {
                        override fun populateItems(itemView: RepeatingView) {
                            super.populateItems(itemView)
                            populateNavbarRightItems(itemView)
                        }
                    }
                collapseItemView.add(navbarRight)
            }
        }
        navbar.setPosition(NavbarPosition.FIXED_TOP)
        this.add(navbar)
        this.add(FlashMessagePanel("flashMessages"))
        createModal()
    }

    override fun onBeforeRender() {
        super.onBeforeRender()
        if (null == getGitkiSession().user) {
            // TODO: Remove
            Visits.visit(this, NonStatelessPrintingVisitor())
        }
    }

    protected fun populateNavbarLeftItems(itemView: RepeatingView) {
        itemView.add(HistoryPageItem(itemView.newChildId()))
    }

    protected fun populateNavbarRightItems(itemView: RepeatingView) {
        itemView.add(AdminDropdownItem(itemView.newChildId()))
        itemView.add(UserDropdownItem(itemView.newChildId()))
        itemView.add(SignInPageLinkItem(itemView.newChildId()))
    }

    private fun createModal() {
        this.add(ModalRequestBehavior(MODAL_ID))
        val modalContainer = WebMarkupContainer(MODAL_ID)
        modalContainer.outputMarkupId = true
        this.add(modalContainer)
    }

    companion object {

        const val MODAL_ID = "modal"
    }
}