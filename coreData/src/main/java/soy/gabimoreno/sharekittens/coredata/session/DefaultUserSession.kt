package soy.gabimoreno.sharekittens.coredata.session

import soy.gabimoreno.sharekittens.coredomain.session.UserSession

class DefaultUserSession : UserSession {

    private var foo = false

    override fun isFoo(): Boolean = foo

    override fun setFoo(b: Boolean) {
        this.foo = b
    }
}
