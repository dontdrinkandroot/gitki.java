package net.dontdrinkandroot.gitki.wicket.dataprovider

import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.service.user.UserService
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider
import org.apache.wicket.injection.Injector
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.spring.injection.annot.SpringBean

class UserDataProvider : SortableDataProvider<User, String>() {

    @SpringBean
    private lateinit var userService: UserService

    init {
        Injector.get().inject(this)
    }

    override fun iterator(first: Long, count: Long): Iterator<User> {
        var sort = this.sort
        if (null == sort) sort = SortParam("id", true)
        return userService.find(first, count, sort.property, sort.isAscending)
    }

    override fun size(): Long = userService.findCount()

    override fun model(user: User): IModel<User> = Model(user)

    override fun detach() {}
}