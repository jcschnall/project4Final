import React, {Component} from 'react'
import axios from 'axios'
import UsersList from './components/UsersList'
import NewUserForm from './components/NewUserForm'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import { Security, ImplicitCallback } from '@okta/okta-react';
import Home from './Home';


class App extends Component {


    const config = {
      issuer: 'https://dev-695185.oktapreview.com/oauth2/default',
      redirect_uri: window.location.origin + '/implicit/callback',
      client_id: '{clientId}'
    }

    state = {
        users: []
    }


    async componentWillMount() {
        const response = await axios.get("/users")
        this.setState({
            users: response.data,
            response
        })

    }

    createUser = async (newUser) => {
        try {
            const response = await axios.post('/users', newUser)

            const newUserDB = response.data
            const updatedUsersList = [...this.state.users]
            updatedUsersList.push(newUserDB)

            this.setState({users: updatedUsersList})

        } catch (error) {
            console.log("Error creating new User")
        }
    }


    deleteUser = async (userId, index) => {
        try {
            await axios.delete(`/users/${userId}`)

            const updatedUsersList = [...this.state.users]
            updatedUsersList.splice(index, 1)

            this.setState({users: updatedUsersList})

        } catch (error) {
            console.log(`Error deleting User with ID: ${userId}`)
        }
    }

    render() {

        const NewUserFormComponent = () => (
            <NewUserForm createUser={this.createUser}/>
        )

        const UsersListComponent = () => (
            <UsersList
                users={this.state.users}
                deleteUser={this.deleteUser}/>
        )


        /*
        const UserByEmail = () => (

            function onClickReturnUserID(){
                return this.props.user.id
            }

            id = user.props.user.id
            <UsersList
                users={this.state.users[id]}
                deleteUser={this.deleteUser}/>
        )
         <Route exact path="/byEmail" render={UserByEmail}/>

         Link to="/byEmail"
        */




        return (
            <Router>
                <Switch>
                    <Route exact path="/list" render={UsersListComponent}/>
                    <Route exact path="/logIn" render={NewUserFormComponent}/>
                </Switch>
            </Router>

            //okta o-auth
             <Router>
                    <Security issuer={config.issuer}
                              client_id={config.client_id}
                              redirect_uri={config.redirect_uri}
                    >
                      <Route path='/' exact={true} component={Home}/>
                      <Route path='/implicit/callback' component={ImplicitCallback}/>
                    </Security>
             </Router>

        )
    }
}

export default App;
