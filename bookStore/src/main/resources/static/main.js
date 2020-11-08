let messageApi = Vue.resource('/book{/id}');
let booksApi = Vue.resource('/book/sorted{/id}');

function getIndex(list, id) {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
};

Vue.component('add-book-form', {
    props: ['books','bookAttr'],
    data: function(){
      return{
          id : '',
          title : '',
          genre : '',
          date :'',
          coast : ''
      }
    },
    watch:{
        bookAttr:function (newVal, oldVal) {
            this.id = newVal.id;
            this.title = newVal.title;
            this.genre = newVal.genre;
            this.date = newVal.date;
            this.coast = newVal.coast;
        }
    },
    template: '<div>' +
        '<input type="text" placeholder="Book title" v-model="title"><br>' +
        '<input type="text" placeholder="Book genre" v-model="genre"><br>' +
        '<input type="date" placeholder="Book date" v-model="date"><br>' +
        '<input type="number" placeholder="Book coast" v-model="coast"><br>' +
        '<input type="button" value="Save" @click="save">' +
        '</div>',
    methods: {
        save: function () {
            let book = {
                title: this.title,
                genre: this.genre,
                date: this.date,
                coast: this.coast
            }
            if (this.id) {
                messageApi.update({id: this.id}, book).then(result =>
                    result.json().then(data => {
                        let index = getIndex(this.books, data.id);
                        this.books.splice(index, 1, data);
                        this.id = '';
                        this.title = '';
                        this.genre = '';
                        this.date = '';
                        this.coast = '';
                    })
                )
            } else {
                messageApi.save({}, book).then(result =>
                    result.json().then(data => {
                        this.books.push(data);
                        this.title = '';
                        this.genre = '';
                        this.date = '';
                        this.coast = '';
                    })
                )
            }
        }

    }
});

Vue.component('table-row', {
    props: ['book','editMethod','books'],
    template: '<tr>' +
        '<td>{{book.title}}</td>' +
        '<td>{{book.genre}}</td>' +
        '<td>{{book.date}}</td>' +
        '<td>{{book.coast}}</td>' +
        '<input type="button" value="Edit" @click="edit">' +
        '<input type="button" value="X" @click="del">' +
        '</tr>',
    methods: {
        edit: function () {
            this.editMethod(this.book);
        },
        del : function () {
            messageApi.remove({id: this.book.id}).then(result =>{
                if (result.ok){
                    this.books.splice(this.books.indexOf(this.book),1);
                }
            })
        }
    }
});

Vue.component('books-table',{
    props:['books'],
    data: function(){
      return {
          book: null
      }
    },
    template:'<div>'+
        '<add-book-form :books="books" :bookAttr="book"></add-book-form>' +
        '<table>' +
        '<th>Название</th>' +
        '<th>Жанр</th>' +
        '<th @click="sortByDate">Дата выпуска</th>' +
        '<th @click=sortByPrice>Цена </th>' +
        '<th>Delete/Edit</th>' +
        '<table-row v-for="book in books" :editMethod="editMethod" :key="book.id" :book="book" :books="books"/>' +
        '</table>'+
        '</div>',
    methods:{
        editMethod: function (book) {
            this.book = book;
        },
        sortByDate: function () {
            this.books.length = 0;
            booksApi.get({id: 1}).then(result =>
                result.json().then(data => {
                    data.forEach(book => this.books.push(book))
                })
            );
        },
        sortByPrice: function () {
            this.books.length = 0;
            booksApi.get({id: 2}).then(result =>
                result.json().then(data => {
                    data.forEach(book => this.books.push(book))
                })
            );
        }
    }
});


const app = new Vue({
    el: '#app',
    template: '<div>' +
        '<books-table :books="books"></books-table>'+
        '</div>',
    data: {
        books: []
    },
    routes: Routes =[
        {
            path:'book'
        }
    ],
    created: function () {
        messageApi.get().then(result =>
            result.json().then(data => {
                data.forEach(book => this.books.push(book))
            })
        );
    }

});