
const button = document.getElementById('gotoregistration');
// Add a click event listener to the button
button.addEventListener('click', function() {
        window.location.href = "/register";
});

// Listen for the form submission
document.getElementById('loginForm').addEventListener('submit', function(e) {
  e.preventDefault(); // Prevent the default form submission behavior

  // Collect the form data
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  // Use Fetch API to send a POST request to the login endpoint.
  // Change '/login' to your actual endpoint if needed.
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
    return response.text(); // Adjust to response.json() if your server returns JSON.
  })
  .then(data => {
    // Display success message or handle further navigation
        //store token
        localStorage.setItem('jwtToken', data);
        // Display success message
        document.getElementById('message').innerText = 'Signup successful!';

        // Redirect to the home page after a short delay (e.g., 0.5 seconds)
        setTimeout(() => {
            window.location.href = "/"; // Redirect to the root path
        }, 1500); // 1500ms = 1.5 seconds

  })
  .catch(error => {
    console.error('Error:', error);
    document.getElementById('message').innerText = 'Login failed. Please try again.';
  });
});