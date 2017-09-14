'use strict';

import React, {Component} from "react";
const client = require('./client');

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {users: []};
    }

    componentDidMount() {
        client({method: 'GET', path: 'api/users'}).done(response => {
            this.setState({users: response.entity._embedded.users});
        })
        ;
    }

    render() {
        return (
            <UserList users={this.state.users}/>
        )
    }
}

class UserList extends Component {
    render() {
        var users = this.props.users.map(user =>
            <User key={user._links.self.href} user={user}/>
        );
        return (
            <table>
                <tbody>
                <tr>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Phone</th>
                </tr>
                {users}
                </tbody>
            </table>
        )
    }
}

class User extends Component {
    render() {
        return (
            <tr>
                <td>{this.props.user.email}</td>
                <td>{this.props.user.name}</td>
                <td>{this.props.user.phone}</td>
            </tr>
        )
    }
}

export default App;