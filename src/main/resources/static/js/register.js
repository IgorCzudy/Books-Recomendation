
// Listen for the form's submit event
document.getElementById('registerForm').addEventListener('submit', function (e) {
  e.preventDefault(); // Prevent the default form submission
  // Gather form data
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
  const repeatedPassword = document.getElementById('repeatedPassword').value;

  // Send a POST request using the Fetch API
  fetch('/api/register', {  // Change '/register' to your actual registration endpoint
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
    return response.text();  // or response.json() if expecting JSON
  })
  .then(data => {
    document.getElementById('message').innerText = 'Signup successful!';
    setTimeout(() => {
        window.location.href = "/login";
    }, 500); // 1500ms = 1.5 seconds

  })
  .catch(error => {
    console.error('Error:', error);
    document.getElementById('message').innerText = 'Signup failed. Please try again.';
  });
});