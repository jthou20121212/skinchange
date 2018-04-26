package com.jthou.demo

data class Book(val name: String) {

    companion object {
        fun books(): Array<Book?> {
            val books = arrayOfNulls<Book>(13)
            books[0] = Book("小窗幽记")
            books[1] = Book("伤心咖啡馆之歌")
            books[2] = Book("遗失的镭")
            books[3] = Book("体育馆杀人")
            books[4] = Book("暹罗连体人之谜")
            books[5] = Book("求道者密室")
            books[6] = Book("死亡通知单")
            books[7] = Book("白夜行")
            books[8] = Book("犯罪心理学")
            books[9] = Book("神探夏洛克")
            books[10] = Book("娱乐至死")
            books[11] = Book("这个世界会好吗")
            books[12] = Book("泥步修行")
            return books
        }
    }

}
