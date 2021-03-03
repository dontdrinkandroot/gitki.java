package net.dontdrinkandroot.gitki.wicket.page.admin

import net.dontdrinkandroot.gitki.model.GitUser
import net.dontdrinkandroot.gitki.model.LockInfo
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.service.lock.LockService
import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.kmodel.wrap
import net.dontdrinkandroot.wicket.model.property
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.model.StringResourceModel
import org.apache.wicket.model.util.ListModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.spring.injection.annot.SpringBean

@Instantiate(Role.ADMIN)
class LockListPage(parameters: PageParameters) : DecoratorPage<List<LockInfo>>(parameters) {

    @SpringBean
    private lateinit var lockService: LockService

    override fun createTitleModel() = StringResourceModel("gitki.locks").wrap()

    override fun onInitialize() {
        super.onInitialize()
        this.model = ListModel(lockService.list())
        this.add(object : ListView<LockInfo>("locks", model) {
            override fun populateItem(item: ListItem<LockInfo>) {
                item.add(Label("path", item.model.property(LockInfo::path)))
                item.add(Label("user", item.model.property(LockInfo::user).property(GitUser::email)))
                item.add(Label("expiry", item.model.property(LockInfo::expiry)))
            }
        })
    }
}