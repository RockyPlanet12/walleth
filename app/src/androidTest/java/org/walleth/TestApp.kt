package org.walleth

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import org.ligi.walleth.App
import org.ligi.walleth.data.BalanceProvider
import org.ligi.walleth.data.ExchangeRateProvider
import org.ligi.walleth.data.FixedValueExchangeProvider
import org.ligi.walleth.data.TransactionProvider
import org.ligi.walleth.data.addressbook.AddressBook
import org.ligi.walleth.data.syncprogress.SyncProgressProvider
import org.ligi.walleth.data.syncprogress.WallethSyncProgress

class TestApp : App() {

    override fun createKodein() = Kodein.Module {
        bind<AddressBook>() with singleton { AddressBookWithTestEntries() }
        bind<BalanceProvider>() with singleton { balanceProvider }
        bind<TransactionProvider>() with singleton { TransactionProviderWithTestData(instance()) }
        bind<ExchangeRateProvider>() with singleton { fixedValueExchangeProvider }
        bind<SyncProgressProvider>() with singleton {
            SyncProgressProvider().apply {
                setSyncProgress(WallethSyncProgress(true, 42000, 42042))
            }
        }
    }

    override fun executeCodeWeWillIgnoreInTests() = Unit

    companion object {

        val fixedValueExchangeProvider = FixedValueExchangeProvider()
        val balanceProvider = BalanceProvider()
    }
}