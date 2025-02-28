const searchBar = document.getElementById('searchFild');
const bookList = document.getElementById('book-list');
const authButton = document.getElementById('authButton');
const recommendationButton = document.getElementById('recommendationButton');


function updateAuthButton() {
    const token = localStorage.getItem('jwtToken');
    authButton.textContent = token ? 'Logout' : 'Login';
}

recommendationButton.addEventListener('click', () => {
    setTimeout(() => window.location.href = '/recommendation', 500);
})

authButton.addEventListener('click', () => {
    const redirectUrl = localStorage.getItem('jwtToken') ? '/' : '/login';
    localStorage.removeItem('jwtToken');
    setTimeout(() => window.location.href = redirectUrl, 500);
});

updateAuthButton();

function createRatingStars(bookId, currentRating) {
    const ratingDiv = document.createElement("div");
    ratingDiv.classList.add("rating");
    ratingDiv.dataset.bookId = bookId;

    for (let i = 1; i <= 5; i++) {
        const star = document.createElement("span");
        star.classList.add("star");
        star.innerHTML = "☆";
        star.dataset.value = i;
        if (i <= currentRating) star.classList.add('active');

        star.addEventListener('click', () => rateBook(bookId, i, ratingDiv));
        ratingDiv.appendChild(star);
    }
    return ratingDiv;
}

function rateBook(bookId, rating, ratingDiv) {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
        alert('Please log in to rate books!');
        window.location.href = '/login';
        return;
    }

    fetch(`/books/${bookId}/rate?rating=${rating}`, {
        method: 'POST',
        credentials: 'include',
        headers: { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' }
    })
    .then(response => {
        if (!response.ok) throw new Error('Network response was not ok');
    })
    .catch(error => console.error('Error:', error));

    // Update star rating UI
    ratingDiv.querySelectorAll('.star').forEach(star => {
        star.classList.toggle('active', parseInt(star.dataset.value) <= rating);
    });
}

function fetchCurrentRating(bookId) {
    const token = localStorage.getItem('jwtToken');
    return fetch(`/books/${bookId}/rate`, {
        method: 'GET',
        headers: { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' }
    })
    .then(response => response.ok ? response.json() : Promise.reject('Failed to fetch rating'))
    .catch(error => {
        console.error('Error fetching rating:', error);
        return 0; // Default rating if fetch fails
    });
}

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

        const currentRating = await fetchCurrentRating(book.id);
        bookDiv.appendChild(createRatingStars(book.id, currentRating));
        bookList.appendChild(bookDiv);
    });
}

function fetchBooks(url) {
    fetch(url)
        .then(response => response.json())
        .then(displayBooks)
        .catch(error => console.error("Error fetching books:", error));
}

searchBar.addEventListener('input', () => fetchBooks(`/books/search?regex=${searchBar.value}`));
fetchBooks('/books');
