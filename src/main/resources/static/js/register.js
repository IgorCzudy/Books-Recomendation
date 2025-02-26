
document.getElementById('registerForm').addEventListener('submit', function (e) {
  e.preventDefault();

  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
  const repeatedPassword = document.getElementById('repeatedPassword').value;

  fetch('/api/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ username, password, repeatedPassword })
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response;
  })
  .then(text => {
    document.getElementById('message').innerText = 'Signup successful!';
    setTimeout(() => {
        window.location.href = "/login";
    }, 500);

  })
  .catch(error => {
    console.error('Error:', error);
    document.getElementById('message').innerText = 'Signup failed. Please try again.';
  });
});