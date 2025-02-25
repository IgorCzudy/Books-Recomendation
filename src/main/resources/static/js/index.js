   const searchBar = document.getElementById('searchFild');

    const bookList = document.getElementById('book-list');

    const authButton = document.getElementById('authButton');

    function updateAuthButton() {
        const token = localStorage.getItem('jwtToken');
        authButton.textContent = token ? 'Logout' : 'Login';
    }

    authButton.addEventListener('click', () => {
        if (localStorage.getItem('jwtToken')) {
            // Perform logout
            localStorage.removeItem('jwtToken');
            setTimeout(() => {
                window.location.href = '/';
            }, 500); // 500ms = 0.5 seconds
        }
        else {
            setTimeout(() => {
                window.location.href = '/login';
            }, 500); // 500ms = 0.5 seconds
        }
    });

    updateAuthButton();

searchBar.addEventListener('input', function() {
    fetch(`/books/search?regex=${searchBar.value}`)
      .then(response => response.json())
    .then(books => {
        const bookList = document.getElementById("book-list");
        bookList.innerHTML = ""; // Clear any existing content

        books.forEach(book => {
            const bookDiv = document.createElement("div");
            bookDiv.classList.add("book");

            // Book details
            bookDiv.innerHTML = `
                <img src="${book.imgURL}" alt="${book.title}">
                <h3>${book.title}</h3>
                <p><strong>Author:</strong> ${book.author}</p>
            `;

            // Add rating stars
            const ratingDiv = document.createElement("div");
            ratingDiv.classList.add("rating");
            ratingDiv.dataset.bookId = book.id;

            // Create 5 stars
            for (let i = 1; i <= 5; i++) {
                const star = document.createElement("span");
                star.classList.add("star");
                star.innerHTML = "☆";
                star.dataset.value = i;

                // Add click handler
                star.addEventListener('click', function() {
                    // Remove active class from all stars in this rating
                    this.parentNode.querySelectorAll('.star').forEach(s =>
                        s.classList.remove('active')
                    );

                    // Activate clicked star and all previous stars
                    let current = this;
                    while(current) {
                        current.classList.add('active');
                        current = current.previousElementSibling;
                    }

                    // Send rating to server with error handling
                    const token = localStorage.getItem('jwtToken');
                    fetch(`/books/${book.id}/rate?rating=${this.dataset.value}`, {
                        method: 'POST',
                        credentials: 'include',
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json'  // Optional, depending on your API
                        }
                    })
                    .then(response => {
                        if (!response.ok) {
                            if (response.status === 403) {
                                alert('Please log in to rate books!');
                                window.location.href = '/login'; // Redirect to login
                                return;
                            }
                            throw new Error('Network response was not ok');
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
                });

                ratingDiv.appendChild(star);
            }

            // Fetch the current rating for the book
            const token = localStorage.getItem('jwtToken');
            fetch(`/books/${book.id}/rate`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch the current rating');
                }
                return response.json();
            })
            .then(currentRating => {
                // Set the active stars based on the current rating
                ratingDiv.querySelectorAll('.star').forEach(star => {
                    if (parseInt(star.dataset.value) <= currentRating) {
                        star.classList.add('active');
                    }
                });
            })
            .catch(error => {
                console.error('Error fetching current rating:', error);
            });

            // Add rating stars to the book
            bookDiv.appendChild(ratingDiv);
            bookList.appendChild(bookDiv);
        });
      })

      .catch(error => console.error("Error fetching books:", error));
});


    // Fetch books from the API and display them
    fetch('/books')
      .then(response => response.json())
      .then(books => {
        const bookList = document.getElementById("book-list");
        bookList.innerHTML = ""; // Clear any existing content

        books.forEach(book => {
            const bookDiv = document.createElement("div");
            bookDiv.classList.add("book");

            // Book details
            bookDiv.innerHTML = `
                <img src="${book.imgURL}" alt="${book.title}">
                <h3>${book.title}</h3>
                <p><strong>Author:</strong> ${book.author}</p>
            `;

            // Add rating stars
            const ratingDiv = document.createElement("div");
            ratingDiv.classList.add("rating");
            ratingDiv.dataset.bookId = book.id;

            // Create 5 stars
            for (let i = 1; i <= 5; i++) {
                const star = document.createElement("span");
                star.classList.add("star");
                star.innerHTML = "☆";
                star.dataset.value = i;

                // Add click handler
                star.addEventListener('click', function() {
                    // Remove active class from all stars in this rating
                    this.parentNode.querySelectorAll('.star').forEach(s =>
                        s.classList.remove('active')
                    );

                    // Activate clicked star and all previous stars
                    let current = this;
                    while(current) {
                        current.classList.add('active');
                        current = current.previousElementSibling;
                    }

                    // Send rating to server with error handling
                    const token = localStorage.getItem('jwtToken');
                    fetch(`/books/${book.id}/rate?rating=${this.dataset.value}`, {
                        method: 'POST',
                        credentials: 'include',
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json'  // Optional, depending on your API
                        }
                    })
                    .then(response => {
                        if (!response.ok) {
                            if (response.status === 403) {
                                alert('Please log in to rate books!');
                                window.location.href = '/login'; // Redirect to login
                                return;
                            }
                            throw new Error('Network response was not ok');
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
                });

                ratingDiv.appendChild(star);
            }

            // Fetch the current rating for the book
            const token = localStorage.getItem('jwtToken');
            fetch(`/books/${book.id}/rate`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch the current rating');
                }
                return response.json();
            })
            .then(currentRating => {
                // Set the active stars based on the current rating
                ratingDiv.querySelectorAll('.star').forEach(star => {
                    if (parseInt(star.dataset.value) <= currentRating) {
                        star.classList.add('active');
                    }
                });
            })
            .catch(error => {
                console.error('Error fetching current rating:', error);
            });

            // Add rating stars to the book
            bookDiv.appendChild(ratingDiv);
            bookList.appendChild(bookDiv);
        });
      })
      .catch(error => console.error("Error fetching books:", error));
