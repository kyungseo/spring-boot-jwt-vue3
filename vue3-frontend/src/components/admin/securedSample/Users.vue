<template>
    <div>
        <table class="table">
            <thead>
                <tr>
                <th scope="col">ID</th>
                <th scope="col">userame</th>
                <th scope="col">Email</th>
                <th scope="col">Phone</th>
                <th scope="col">Actions</th>
                </tr>
            </thead>
            <tbody v-for="(user, index) in users" :key="index">
                <tr>
                    <td>{{user.id}}</td>
                    <td>{{user.userame}}</td>
                    <td>{{user.email}}</td>
                    <td>{{user.phoneNumber}}</td>
                    <td><a :href="'/users/' + user.id" class="btn btn-primary">Edit</a></td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import UserService from '../../../services/user.service'

export default {
    name: 'users',
    data() {
        return {
            users: []
        }
    },
    methods: {
        retrieveUsers() {
            UserService.getAll()
                .then(response => {
                    this.users = response.data
                })
                .catch(e => {
                    alert(e)
                })
        }
    },
    mounted() {
        this.retrieveUsers()
    }
}
</script>
