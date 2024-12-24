package com.dtl.emojibatterywidget.domain.usecase

import com.dtl.emojibatterywidget.R
import com.dtl.emojibatterywidget.domain.layer.LanguageModel
import javax.inject.Inject

class GetListLanguageUseCase @Inject constructor() :
    UseCase<GetListLanguageUseCase.Param, List<LanguageModel>>() {

    open class Param() : UseCase.Param()

    override suspend fun execute(param: Param): List<LanguageModel> = listOf(
        LanguageModel("en", R.drawable.img_eng, R.string.english),
        LanguageModel("vi", R.drawable.img_vie, R.string.vietnamese),
        LanguageModel("hi", R.drawable.img_hindi, R.string.hindi),
        LanguageModel("es", R.drawable.img_es, R.string.spanish),
        LanguageModel("fr", R.drawable.img_fr, R.string.french),
        LanguageModel("pt", R.drawable.img_por, R.string.portuguese)
    )
}