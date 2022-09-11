# MoviesUniverse

Приложение для поиска фильмов, актеров и информации о них.

<details>
<summary>Technologies</summary>

- Architecture:	MVVM, Single Activity, Fragments, XML

- Asynchronous:	Coroutine Flow

- Navigation: Cicerone

- DI: Koin

- Network: Retrofit

- Json: Kotlin serialization

- Data Base: Room

- Image Loading: Glide
	
- Other: Paging 3

</details>

<details><summary>Requirements</summary>

- Мин версия: Andorid 6.0 <br>
- Ориентация: Портретная <br>
- Локализация: Русский язык <br>

- API: https://kinopoiskapiunofficial.tech/ 

- Проект состоит из 6 экранов и нижнего нав. меню.

	<details><summary>Сплэш экран</summary>
		
	- Задержка для показа 800 мс.

	</details>

	<details><summary>Таб экран "Главная"</summary>

	- Вход: сплеш экран, таб нижнего нав. меню "Главная"
	- Представляет список фильмов.
		
	- Наличие пагинации.

	- Наличие кэширования в базе данных.
	- При первом открытии экрана происходит запрос в сеть на загрузку фильмов, при последующих - загрузка из кэша.
	- В процессе запроса в сеть - отображается состояние загузки.

	- По клику на фильм - открывается экран с детальной информацией о фильме, с возможностью возврата.

	- Присутствует swipe refresh layout. (для обновления списка с запросом в сеть)
	- В процессе обновления - отображается состояние обновления.
	- При обновлении с swipe refresh layout очищается кэш и происходит запрос в сеть для обновления данных.
	- При обновлении с swipe refresh layout и возникновении ошибки - отображается snack bar.

	- При входе со сплеш экрана, при отсутствии соединения с интернетом и отсутсвия данных в кэше - показывается состояние ошибки с возможностью повторного запроса.
	- При повторном запросе - отображается состояние загузки.
		
	- При нажатии на кнопку back - происходит выход из приложения.

	</details>

	<details><summary>Таб экран "Фильмы" для поиска фильмов</summary>

	- Вход: таб нижнего нав. меню "Поиск"
	- Содержит поле для ввода текста.
	- Поиск производится по вхождению подстроки в строку (название фильма).

	- При выполнении поиска происходит запрос в сеть.
	- В процессе загрузки - отображается состояние загузки.
	- Результат поиска отображается списком.

	- При выполнении запроса и возникновении ошибки - отображается соответствующее состояние.

	- При первом входе на экран - отображается соответсвущее состояние.
	- При отсутствии результатов поиска - отображается соответствующее состояние.

	- По клику на фильм - открывается экран с детальной информацией фильма.

	</details>

	<details><summary>Экран с детальной информацией фильма</summary>

	- Вход: главный экран, экран поиска фильмов.
	
	- Наличие кэширования в базе данных.
	- При первом открытии одного и того же экрана происходит запрос в сеть на загрузку, при последующих - загрузка из кэша.
	- В процессе запроса в сеть - отображается состояние загузки.
	- При возникновении ошибки во время запроса - отображается сообветсвующее состояние.

	- Содержит navigate up кнопку "Назад" для возврата на предыдущий экран.
	- При нажатии на кнопку back - происходит переход на предыдущий экран.

	</details>

	<details><summary>Таб экран "Актеры" для поиска актеров</summary>

	- Вход: таб нижнего нав. меню "Актеры", navigate up кнопка "Назад" экрана детальной информации о актере.
	- Содержит поле для ввода текста.
	- Поиск производится по вхождению подстроки в строку (имя и фамилия актера).

	- При выполнении поиска происходит запрос в сеть.
	- В процессе загрузки - отображается состояние загузки.
	- Результат поиска отображается списком.

	- При выполнении запроса и возникновении ошибки - отображается snack bar.

	- При первом входе на экран - отображается соответсвущее состояние.
	- При отсутствии результатов поиска - отображается соответствующее состояние.

	- По клику на элемент - открывается экран с детальной информацией актера.

	</details>

	<details><summary>Экран с детальной информацией актера</summary>

	- Вход: экран избранное, экран с детальной информацией фильма

	- Содержит список фильмов, в которых принимал участие актер.
	- При клике на фильм из списка - открывается экран с детальной информацией о фильме.

	- Содержит navigate up кнопку "Назад" для возврата на экран поиска актеров.
	- При нажатии на кнопку back - происходит переход на предыдущий экран.

	</details>

</details>
