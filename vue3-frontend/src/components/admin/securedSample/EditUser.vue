<template>
    <div v-if="currentCustomer">
        <div class="mb-3">
                <label for="firstName" class="form-label">First Name</label>
                <input type="text" class="form-control" id="firstName" required name="firstName" v-model="currentCustomer.firstName">
            </div>
            <div class="mb-3">
                <label for="lastName" class="form-label">Last Name</label>
                <input type="text" class="form-control" id="lastName" required name="lastName" v-model="currentCustomer.lastName">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" required name="email" v-model="currentCustomer.email">
            </div>
            <div class="mb-3">
                <label for="phone" class="form-label">Phone</label>
                <input type="text" class="form-control" id="phone" required name="phone" v-model="currentCustomer.phone">
            </div>
            <div class="mb-3">
                <button @click="updateCustomer" class="btn btn-primary me-3">Update</button>
                <button @click="deleteCustomer" class="btn btn-danger">Delete</button>
            </div>
            <div class="alert alert-success" role="alert" v-if="message">
                {{message}}
            </div>
    </div>
</template>

<script>
import UserService from '../../../services/user.service'

export default {
    name: 'edit-user',
    data() {
        return {
            currentCustomer: null,
            message: ''
        }
    },
    methods: {
        getUser(id) {
            UserService.get(id)
                .then(response => {
                    this.currentCustomer = response.data
                })
                .catch(e => {
                    alert(e)
                })
        },
        updateUser() {
            UserService.update(this.currentCustomer.id, this.currentCustomer)
                .then(() => {
                    this.message = 'The customer was updated successfully!'
                })
                .catch(e => {
                    alert(e)
                })
        },
        deleteUser() {
            UserService.delete(this.currentCustomer.id)
                .then(() => {
                    this.$router.push({name: 'customers'})
                })
                .catch(e => {
                    alert(e)
                })
        }
    },
    mounted() {
        this.getUser(this.$route.params.id)
    }
}
</script>
