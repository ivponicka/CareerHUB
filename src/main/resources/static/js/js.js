let count = 1;
function showheaderItem(id) {
  document.querySelectorAll('.header-main-item').forEach((item) => {
    if (count == item.dataset.id) {
      item.style.display = "block";
    } else {
      item.style.display = "none";
    }
  });
}



showheaderItem(count);
document.querySelector('.header-right-btn').addEventListener('click', () => {
  count--;
  if (count < 1) count = 4;
  showheaderItem(count);
});

showheaderItem(count);
document.querySelector('.header-left-btn').addEventListener('click', () => {
  count--;
  if (count < 1) count = 4;
  showheaderItem(count);
});


const allProducts = document.querySelectorAll('.trending-item');
const allFilterBtns = document.querySelectorAll('.filter-btn');

allFilterBtns.forEach((button, index) => {
  button.addEventListener('click', () => {
    resetBtnColor();
    button.classList.add('activeFilterBtn');
    showProducts(index + 1);
  });
});

function showProducts(id) {
  allProducts.forEach((product) => {
    if (id == product.dataset.product) {
      product.style.display = 'block';
    } else {
      product.style.display = "none";
    }
  });
}

function resetBtnColor() {
  allFilterBtns.forEach((button) => {
    button.classList.remove('.activeFilterBtn');
  })
}

showProducts(1);
allFilterBtns[0].classList.add('activeFilterBtn');


function applyDiscount() {
  // Get the discount code input value
  var discountCode = document.getElementById("discount-code").value;

  // Calculate the discount amount based on the discount code (assuming you have logic to do this)
  var discountAmount = calculateDiscount(discountCode);

  // Get the current total amount
  var currentTotal = parseFloat(document.getElementById("totalAmount").innerText.replace("Total: $", ""));

  // Calculate the new total after applying the discount
  var newTotal = currentTotal - discountAmount;
  if (discountAmount > 0) {
    document.getElementById("discountApplied").innerText = "-€ " + discountAmount.toFixed(2);
  } else {
    document.getElementById("discountApplied").innerText = "-";
  }
  // Update the total amount display
  document.getElementById("totalAmount").innerText = "Total: $" + newTotal.toFixed(2);
}

function calculateDiscount(discountCode) {
  // Your logic to calculate the discount amount based on the discount code
  // For example, you might fetch this information from a database or apply a fixed discount
  // For this example, let's assume a fixed discount amount of $10 for demonstration purposes
  if (discountCode === "jewex") {
    return 10;
  } else {
    return 0; // No discount
  }
}