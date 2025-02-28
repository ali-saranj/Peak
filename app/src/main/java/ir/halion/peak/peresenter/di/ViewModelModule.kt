package ir.halion.peak.peresenter.di

import ir.halion.peak.peresenter.focus.FocusViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

var ViewModelModule = module {
    viewModel { FocusViewModel() }
}