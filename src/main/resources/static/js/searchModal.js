document.addEventListener("DOMContentLoaded", function () {
    const searchModal = new bootstrap.Modal(document.getElementById('searchModal'));
    const searchInput = document.getElementById('companyIdInput');
    const searchButton = document.getElementById('searchButton');

    // 검색 결과 컨테이너를 화면 중앙 상단에 고정
    const searchResults = document.createElement("div");
    searchResults.className = "search-results list-group position-fixed bg-white border rounded shadow-sm p-2";
    searchResults.style.width = "300px";
    searchResults.style.top = "10%";
    searchResults.style.left = "50%";
    searchResults.style.transform = "translateX(-50%)";
    searchResults.style.zIndex = "2000";
    searchResults.style.display = "none";
    document.body.appendChild(searchResults);

    function openSearchModal(target) {
        searchInput.value = '';
        searchButton.setAttribute("onclick", `goToPage('${target}')`);
        searchResults.innerHTML = "";
        searchResults.style.display = "none";
        searchModal.show();
        setTimeout(() => searchInput.focus(), 200);
    }

    function goToPage(target, entityId) {
        if (!entityId) {
            alert("고유번호를 입력하세요.");
            return;
        }
        window.location.href = `/${target}/${entityId}`;
    }

    searchInput.addEventListener("input", function () {
        const query = searchInput.value.trim();
        if (!query) {
            searchResults.innerHTML = "";
            searchResults.style.display = "none";
            return;
        }

        const apiUrl = searchButton.getAttribute("onclick").includes("fund")
            ? `/api/funds/searchQueryForFundInHeader?query=${query}`
            : `/api/companies/searchQueryForCompanyInHeader?query=${query}`;

        fetch(apiUrl)
            .then(response => response.json())
            .then(results => {
                searchResults.innerHTML = "";
                searchResults.style.display = "block";
                if (results.length === 0) {
                    searchResults.innerHTML = `<div class='list-group-item text-muted'>검색 결과 없음</div>`;
                } else {
                    results.forEach(item => {
                        const [entityId, entityName] = item;
                        const resultItem = document.createElement("button");
                        resultItem.className = "list-group-item list-group-item-action";
                        resultItem.textContent = entityName;
                        resultItem.onclick = function () {
                            searchInput.value = entityName;
                            searchInput.setAttribute("data-entity-id", entityId);
                            searchResults.innerHTML = "";
                            searchResults.style.display = "none";
                        };
                        searchResults.appendChild(resultItem);
                    });
                }
            })
            .catch(error => console.error("검색 오류:", error));
    });

    searchButton.addEventListener("click", function () {
        const entityId = searchInput.getAttribute("data-entity-id");
        if (entityId) {
            goToPage(searchButton.getAttribute("onclick").match(/'([^']+)'/)[1], entityId);
        } else {
            alert("검색 후 항목을 선택해주세요.");
        }
    });

    document.getElementById("searchModal").addEventListener("hidden.bs.modal", function () {
        searchResults.innerHTML = "";
        searchResults.style.display = "none";
    });

    window.openSearchModal = openSearchModal;
});