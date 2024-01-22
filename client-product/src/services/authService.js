async function loginUser(credentials) {
    return fetch('http://localhost:8082/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    })
        .then(data => data.json())
}
// eslint-disable-next-line import/no-anonymous-default-export
export default { loginUser };