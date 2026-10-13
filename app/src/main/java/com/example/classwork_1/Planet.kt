package com.example.classwork_1

data class Planet(
    val name: String,
    val description: String,
    val imageUrl: String
)
//
val planets = listOf(
    Planet(
        name = "Меркурий",
        description = "Самая близкая к Солнцу планета и самая маленькая в Солнечной системе. Поверхность покрыта кратерами, атмосфера практически отсутствует.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4a/Mercury_in_true_color.jpg/800px-Mercury_in_true_color.jpg"
    ),
    Planet(
        name = "Венера",
        description = "Вторая планета от Солнца. Из-за плотной атмосферы из углекислого газа это самая горячая планета — около 475 °C.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Venus_globe.jpg/800px-Venus_globe.jpg"
    ),
    Planet(
        name = "Земля",
        description = "Наш дом — третья планета от Солнца. Единственная известная планета с жизнью и жидкой водой на поверхности.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/The_Earth_seen_from_Apollo_17.jpg/800px-The_Earth_seen_from_Apollo_17.jpg"
    ),
    Planet(
        name = "Марс",
        description = "Красная планета, четвёртая от Солнца. Известна своими полярными шапками, каньонами и потухшими вулканами, включая Олимп.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/OSIRIS_Mars_true_color.jpg/800px-OSIRIS_Mars_true_color.jpg"
    ),
    Planet(
        name = "Юпитер",
        description = "Крупнейшая планета Солнечной системы. Газовый гигант с Большим красным пятном — огромным штормом, длящимся столетиями.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/Jupiter_and_its_shrunken_Great_Red_Spot.jpg/800px-Jupiter_and_its_shrunken_Great_Red_Spot.jpg"
    ),
    Planet(
        name = "Сатурн",
        description = "Шестая планета, знаменитая своей яркой системой колец из льда и камня. Газовый гигант с множеством спутников.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c7/Saturn_during_Equinox.jpg/800px-Saturn_during_Equinox.jpg"
    ),
    Planet(
        name = "Уран",
        description = "Ледяной гигант, седьмая планета. Вращается «лёжа на боку» из-за наклона оси около 98 градусов.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3d/Uranus2.jpg/800px-Uranus2.jpg"
    ),
    Planet(
        name = "Нептун",
        description = "Восьмая и самая далёкая планета от Солнца. Ледяной гигант с сильными ветрами, достигающими 2100 км/ч.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Neptune_Full.jpg/800px-Neptune_Full.jpg"
    ),
    Planet(
        name = "Плутон",
        description = "Карликовая планета в поясе Койпера. С 2006 года не считается полноценной планетой, но остаётся символом дальних рубежей.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/Pluto_in_True_Color_-_High-Res.jpg/800px-Pluto_in_True_Color_-_High-Res.jpg"
    ),
    Planet(
        name = "Церера",
        description = "Карликовая планета в поясе астероидов между Марсом и Юпитером. Крупнейший объект пояса, содержит водяной лёд.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Ceres_-_RC3_-_Haulani_Crater_%2822381131691%29.jpg/800px-Ceres_-_RC3_-_Haulani_Crater_%2822381131691%29.jpg"
    )
)