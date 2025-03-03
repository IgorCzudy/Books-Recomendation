const bookList = document.getElementById('book-list');
const homeButton = document.getElementById('homeButton');

homeButton.addEventListener('click', () => {
    setTimeout(() => window.location.href = '/', 500);
})

function displayBooks(books) {
    bookList.innerHTML = "";
    books.forEach(async book => {
        const bookDiv = document.createElement("div");
        bookDiv.classList.add("book");
        bookDiv.innerHTML = `
            <img src="${book.imgURL}" alt="${book.title}">
            <h3>${book.title}</h3>
            <p><strong>Author:</strong> ${book.author}</p>
        `;

        bookList.appendChild(bookDiv);
    });
}

function fetchRecommendedBooks(url) {
        const token = localStorage.getItem('jwtToken');
        if (!token) {
            alert('Please log to get recommendation');
            window.location.href = '/login';
            return;
        }

        fetch('/api/recommendation', {
            method: 'Get',
            credentials: 'include',
            headers: { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' }
        })
        .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch recommendations');
                }
                return response.json();
            })
            .then(data => {
                const container = document.getElementById('recommendationContainer');

                if (!data || data.length === 0) {  // Check if data is empty
                        container.innerHTML = "<p>I need to know what you like. Add more positive reviews to get recommendations.</p>";
                        container.style.display = "block"; // Show the message
                    } else {
                        container.style.display = "none"; // Hide if data exists
                        displayBooks(data);
                    }
            })
            .catch(error => {
                console.error('Error fetching recommendations:', error);
            });
}

fetchRecommendedBooks('/api/recommendation');
