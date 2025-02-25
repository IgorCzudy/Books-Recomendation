
const button = document.getElementById('gotoregistration');
button.addEventListener('click', function() {
        window.location.href = "/register";
});

document.getElementById('loginForm').addEventListener('submit', function(e) {
  e.preventDefault();

  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  fetch('/api/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ username, password })
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('Login failed');
    }
    return response.json();
  })
  .then(data => {localStorage.setItem('jwtToken', data);
        document.getElementById('message').innerText = 'Signup successful!';

        setTimeout(() => {
            window.location.href = "/";
        }, 500);

  })
  .catch(error => {
    console.error('Error:', error);
    document.getElementById('message').innerText = 'Login failed. Please try again.';
  });
});