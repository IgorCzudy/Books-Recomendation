const bookList = document.getElementById('book-list');

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
                displayBooks(data);  // Assuming the response contains books in `data.books`
            })
            .catch(error => {
                console.error('Error fetching recommendations:', error);
            });
}

fetchRecommendedBooks('/api/recommendation');
