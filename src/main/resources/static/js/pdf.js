var completeTransactionJson = {};
const shopItems = {
    1: "Kurta",
    2: "Nighty",
    3: "N.Suit",
    4: "Lower",
    5: "Capri",
    6: "Shorts",
    7: "Shirt",
    8: "T-shirt",
    9: "Trouser",
    10: "B.",
    11: "Panties",
    12: "U.W",
    13: "Vest",
    14: "Slip",
    15: "Hankey",
    16: "Socks",
    17: "Supporter",
    18: "Track Suit",
    19: "Towel",
    20: "Dhoti",
    21: "Patka",
    22: "Jacket",
    23: "Thermal",
};

function setupDatePicker() {
    const datepicker = document.getElementById("page-date");
    datepicker.value = new Date().toISOString().split('T')[0];
}
function initializeTransactionJson() {
    var n = localStorage.getItem("on_load_counter") || 0;
    completeTransactionJson = {
        transactionNumber: n,
        purchases: []
    };
    localStorage.setItem("on_load_counter", n);
}
function setupFocusBetweenInputPairs() {
    const inputPairElements = document.querySelectorAll(".input-pair");
    inputPairElements.forEach((element, index) => {
        element.addEventListener("keypress", (event) => {
            if (event.key === "Enter") {
                const nextIndex = (index + 1) % inputPairElements.length;
                inputPairElements[nextIndex].focus();
            }
        });
    });
}
function populateShopItemsList() {
    const mapListElement = document.getElementById("map-list");
    Object.keys(shopItems).forEach((key) => {
        const element = `<div>${key} ${shopItems[key]}</div>`;
        mapListElement.innerHTML += element;
    });
}
function sendTransactionData() {
    console.log('POST CALL FOR',completeTransactionJson);
    fetch("/transactions/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(completeTransactionJson),
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        return response.json();
    })
    .then((data) => {
        console.log("Success:", data);
    })
    .catch((error) => {
        console.error("Error:", error);
    });

    console.log(JSON.stringify(completeTransactionJson));
}
function processDownload() {
    document.querySelectorAll(".item-delete").forEach((button) => {
        button.style.display = "none";
    });
    completeTransactionJson["remarks"] = document.getElementById("wild-input").value;
    completeTransactionJson["shopname"] = localStorage.getItem("shopname");
    completeTransactionJson["createdAt"] = document.getElementById("page-date").value;

    document.getElementById("input-and-mapping-containing-div").remove();
    document.querySelector(".item-table").style.maxHeight = "unset";

    window.print();
    window.location.reload();

    sendTransactionData();

    // Update localStorage counters
    let n = parseInt(localStorage.getItem("on_load_counter")) || 0;
    n++;
    localStorage.setItem("on_load_counter", n);
}
function handleWildInputEnter(e) {
    e.preventDefault();
    if (e.key === "Enter") {
        document.getElementById("wild-input").disabled = true;
        document.querySelector(".input-pair").focus();
    }
}
function globalKeyUpHandler(event) {
    const name = event.key;
    if (name === "Shift") {
        document.getElementById("download").click();
    } else if ((name === "z" || name === "Z") && localStorage.getItem("shopname") === "one") {
        document.getElementById("tbtn").click();
    } else if (name === "c" || name === "C") {
        document.getElementById("ttb").click();
    } else if ((name === "m" || name === "M") && localStorage.getItem("shopname") === "one") {
        location.href = "/users/month";
    }
}
function setupServiceWorker() {
    if ("serviceWorker" in navigator) {
        navigator.serviceWorker
                    .register("/service-worker.js")
                    .then(registration => {
                        console.log("ServiceWorker registration successful with scope: ", registration.scope);
                    })
                    .catch(err => {
                        console.log("ServiceWorker registration failed: ", err);
                    });
    }
}
function setupEventListeners() {
    document.getElementById("download").addEventListener("click", processDownload);
    document.getElementById("wild-input").addEventListener("keyup", handleWildInputEnter);
    document.addEventListener("keyup", globalKeyUpHandler);
    setupServiceWorker();
}
window.onload = function () {
    completeTransactionJson["shopname"] = localStorage.getItem("shopname");
    setupDatePicker();
    initializeTransactionJson();
    setupFocusBetweenInputPairs();
    populateShopItemsList();
    setupEventListeners();
};

//------------------------------------------------------------------------------------------ LIST UPDATE FUNCTIONALITY

function updatePageTotals() {
    const itemTotals = document.querySelectorAll("#table-body .item-total");
    const itemQuantities = document.querySelectorAll("#table-body .item-quantity");
    let total = 0;
    itemTotals.forEach((totalElement, index) => {
        const itemTotal = parseInt(totalElement.innerText);
        const itemQuantity = parseInt(itemQuantities[index].innerText);
        if (isNaN(itemTotal)) return;
        total += itemTotal;
    });

    if (total >= 0) {
        document.getElementById("page-total-sum").innerText = total;
        let discount = total / 10
        document.getElementById("page-total-discountMinus").innerText = `-${discount}`;
        total -= discount;
        total = Math.round(total);
        document.getElementById("paget-total-netTotal").innerText = total;
    }

    completeTransactionJson["totalPrice"] = total;
    completeTransactionJson["netPrice"] = total;
}
function increaseTotalItemsCount() {
    const itemQuantities = document.querySelectorAll("#table-body .item-quantity");
    let totalItems = Array.from(itemQuantities).reduce((total, element) => {
        const value = parseInt(element.innerText);
        return isNaN(value) ? total : total + value;
    }, 0);

    document.getElementById("page-total-items").innerText = totalItems;
    completeTransactionJson["totalItems"] = totalItems;
}
function removeNode(id) {
    return completeTransactionJson["purchases"].filter(item => item.itemNumber !== id);
}
function handleDelete(event) {
    const rowId = event.target.closest("tr").id;
    document.getElementById(rowId).remove();
    completeTransactionJson["purchases"] = removeNode(rowId);
    increaseTotalItemsCount();
    updatePageTotals();
}
function addNewItemToTable(uniqueId, itemName, itemPrice, itemQuantity, rowTotal) {
    const newRow = `
        <tr id=${uniqueId} class="item-row">
            <td> <span class="item-name">${itemName}</span> </td>
            <td> <span class="item-price">${itemPrice}</span> </td>
            <td> <span class="item-quantity">${itemQuantity}</span> </td>
            <td>
                <span class="font-weight-semibold item-total">${rowTotal}</span>
                <button class="item-delete btn btn-danger" style="margin-left: 1rem" onclick="handleDelete(event)"> X </button>
            </td>
        </tr>`;
    document.getElementById("table-body").innerHTML += newRow;
}
function updateTotalsAfterNewItem(uniqueId, itemName, itemPrice, itemQuantity, rowTotal) {
    completeTransactionJson["purchases"].push({
        itemNumber: uniqueId,
        itemName,
        itemPrice,
        itemQuantity,
        itemTotalPrice: rowTotal
    });

    increaseTotalItemsCount();
    updatePageTotals();
}
function generateUniqueId() {
    return "id_" + document.querySelectorAll("#table-body >tr").length;
}
function isValidNewItem(number, price, quantity) {
    return number && price && quantity && number > 0 && price > 0 && quantity > 0;
}
function clearNewItemInputs() {
    document.getElementById("new-item-number").value = "";
    document.getElementById("new-item-price-input").value = "";
    document.getElementById("new-item-quantity-input").value = "";
}
function additemNumberToList() {
    const newItemNumber = document.getElementById("new-item-number").value;
    const newItemPrice = document.getElementById("new-item-price-input").value;
    const newItemQuantity = document.getElementById("new-item-quantity-input").value;

    if (!isValidNewItem(newItemNumber, newItemPrice, newItemQuantity)) return;
    clearNewItemInputs();

    const newRowTotal = newItemPrice * newItemQuantity;
    const uniqueId = generateUniqueId();
    const itemName = shopItems[newItemNumber];

    if (typeof itemName === "undefined") return;

    addNewItemToTable(uniqueId, itemName, newItemPrice, newItemQuantity, newRowTotal);
    updateTotalsAfterNewItem(uniqueId, itemName, newItemPrice, newItemQuantity, newRowTotal);
}

function additemNumberToListOnEnter(event) {
    if (event.key === "Enter") {
        additemNumberToList();
    }
}
