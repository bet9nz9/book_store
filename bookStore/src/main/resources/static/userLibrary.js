let userLibrary = Vue.resource('/library{/id}');
let sumOfLibrary = Vue.resource('/library/sum');
let libraryPayment = Vue.resource('/library/payment')

Vue.component('row', {
    props: ['libBook','libraryBooks'],
    template: '<tr>' +
        '<td>{{libBook.title}}</td>' +
        '<td>{{libBook.genre}}</td>' +
        '<td>{{libBook.date}}</td>' +
        '<td>{{libBook.coast}}</td>' +
        '<span>'+
        '<input type="button" value="X" @click="del">' +
        '</span>'+
        '</tr>',
    methods: {
        del : function () {
            userLibrary.remove({id: this.libBook.id}).then(result =>{
                if (result.ok){
                    this.libraryBooks.splice(this.libraryBooks.indexOf(this.libBook),1);
                }
            })
        }
    }
});

Vue.component('libBooks-table',{
    props:['libraryBooks'],
    data: function(){
        return {
            libBook: null
        }
    },
    template:'<div>'+
        '<table>' +
        '<th>Название</th>' +
        '<th>Жанр</th>' +
        '<th @click="sortByDateInLib">Дата выпуска</th>' +
        '<th @click="sortByPriceInLib">Цена </th>' +
        '<th >Delete</th>' +
        '<row v-for="libBook in libraryBooks" :key="libBook.id" :libBook="libBook" :libraryBooks="libraryBooks"/>' +
        '</table>'+
        '</div>',
    methods:{
        sortByDateInLib: function () {
            this.libraryBooks.length = 0;
            userLibrary.get({id: 1}).then(result =>
                result.json().then(data => {
                    data.forEach(book => this.libraryBooks.push(book))
                })
            );
        },
        sortByPriceInLib: function () {
            this.books.length = 0;
            userLibrary.get({id: 2}).then(result =>
                result.json().then(data => {
                    data.forEach(book => this.libraryBooks.push(book))
                })
            );
        }
    }
});

const library = new Vue({
    el: '#userLibrary',
    template: '<div v-if="profile.role.toString() === \'USER\'">' +
        '<h3>Корзина</h3>'+
        '<libBooks-table :libraryBooks="libraryBooks" :sum="sum"></libBooks-table>'+
        '<div><i>Общая сумма: </i><i>{{sum}}</i></div>'+
        '<input type="button" value="Оплатить" @click="payment">'+
        '</div>',
    data: {
        libraryBooks: [],
        profile : frontendData.profile,
        sum: [],
    },
    routes: Routes =[
        {
            path:'library'
        }
    ],
    methods:{
      payment: function () {
          this.libraryBooks.length = 0;
          libraryPayment.get().then(result =>
          result.json().then(data=>{
              data.forEach(book => this.libraryBooks.push(book));
          })
      )

      }
    },
    created: function () {
        userLibrary.get().then(result => {
                result.json().then(data => {
                    data.forEach(book => this.libraryBooks.push(book))
                })
                console.log(this.libraryBooks);
            }
        );
        sumOfLibrary.get().then(result =>
            result.json().then(data =>{
                this.sum = data;
            })
        )
    }
})