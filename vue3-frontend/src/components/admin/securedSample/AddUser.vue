<template>
    <div>
        <div v-if="!submitted">
            <div class="mb-3">
                <label for="firstName" class="form-label">First Name</label>
                <input type="text" class="form-control" id="firstName" required name="firstName" v-model="customer.firstName">
            </div>
            <div class="mb-3">
                <label for="lastName" class="form-label">Last Name</label>
                <input type="text" class="form-control" id="lastName" required name="lastName" v-model="customer.lastName">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" required name="email" v-model="customer.email">
            </div>
            <div class="mb-3">
                <label for="phone" class="form-label">Phone</label>
                <input type="text" class="form-control" id="phone" required name="phone" v-model="customer.phone">
            </div>
            <div class="mb-3">
                <button @click="saveCustomer" class="btn btn-primary">Submit</button>
            </div>
        </div>
        <div v-else>
            <div class="alert alert-success" role="alert">
                Save customer successfully!
            </div>
            <button @click="newCustomer" class="btn btn-primary">Add New Customer</button>
        </div>
    </div>
</template>

<script>
import UserService from '../../../services/user.service'

export default {
    name: 'add-user',
    data() {
        return {
            customer: {
                id: null,
                firstName: "",
                lastName: "",
                email: "",
                phone: ""
            },
            submitted: false
        }
    },
    methods: {
        saveUser() {
            var data = {
                firstName: this.customer.firstName,
                lastName: this.customer.lastName,
                email: this.customer.email,
                phone: this.customer.phone
            }
            UserService.create(data)
                .then(response => {
                    this.customer.id = response.data.id
                    this.submitted = true;
                })
                .catch( e => {
                    alert(e)
                })
        },
        newUser() {
            this.submitted = false
            this.customer = {}
        }
    }
}
</script>
