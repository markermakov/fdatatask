# Матчинг заявок на упрощенной бирже

Основная идея алгоритма заключается в следующем:
  - Есть 2 кейс-класса Client и Order для обработки и хранения данных
  - Во время итерации сквозь файл orders.txt мы берем хэш от типа покупки/продажи с его противоположным значением, т.е. если заявка типа "b", то возвращаем ее противоположность - "s", наименования ценной бумаги, ее цены и кол-ва.
  - Есть глобальная LinkedHashMap (для сохранения порядка добавления ключей), где мы храним имена пользователей как ключи и хэши, привязанные к ним вместе с конкретным заказом, как значения.
  - Взяв конкретную запись (заказ) мы проверяем наличие ее хэша в мапе выше, пройдясь по всем пользователям, в которых он может содержаться в хронологическом порядке. Если есть совпадение - значит заводилась заявка на покупку или продажу конкретной позиции.
  - Далее проверяем не самому себе ли клиент оформил заявку, если так, то ничего не делаем. Иначе - рассчитываем транзакцию, изменяем баланс клиентов в clientsMap, где хранится информация по клиентам. Так как заявки обрабатываются 1 к 1, то после первой успешной обработки выставляется флаг об успешной обработке конкретного заказа, чтобы он не применился к другим заказам по ошибке.
  - Затем пара обработанных хэшей удаляется из общей структуры.
  - Если ключа в общей структуре нет - значит такой заявки еще нет и мы добавляем ее, чтобы в будущем найти по ней совпадение.

Итоговый результат выглядит следующим образом (файл tech_task/result.txt):
```
C1 13366 105 -84 181 -192
C2 9703 363 443 544 149
C3 -7839 337 221 321 347
C4 26477 -196 -422 28 -738
C5 1237 189 184 179 140
C6 13427 -157 -194 149 272
C7 6082 76 58 299 73
C8 -22761 792 880 900 1422
C9 -13222 631 514 879 737
```

Приложение запускается через объект Main, где можно указать пути к входным файлам и куда записать результат.
