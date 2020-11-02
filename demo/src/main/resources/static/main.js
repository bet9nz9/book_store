let messageApi = Vue.resource('/book{/id}')

function getIndex(list, id) {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('add-book-form', {
    props: ['books'],
    template: '<div>' +
        '<input type="text" placeholder="Book title" v-model="title"><br>' +
        '<input type="text" placeholder="Book genre" v-model="genre"><br>' +
        '<input type="text" placeholder="Book date" v-model="date"><br>' +
        '<input type="text" placeholder="Book coast" v-model="coast"><br>' +
        '<input type="button" value="Save" @click="save">' +
        '</div>',
    methods: {
        save: function () {
            let book = {
                title: this.title,
                genre: this.genre,
                date: this.genre,
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
})


const app = new Vue({
    el: '#app',
    template: '<div>' +
        '<add-book-form></add-book-form>' +
        '</div>',
    data: {
        books: []
    }

});