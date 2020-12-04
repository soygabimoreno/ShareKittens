package soy.gabimoreno.sharekittens.coredata.di

import org.koin.dsl.module
import soy.gabimoreno.sharekittens.coredata.session.DefaultUserSession
import soy.gabimoreno.sharekittens.coredomain.session.UserSession

val coreDataModule = module {
    single<UserSession> { DefaultUserSession() }
}
