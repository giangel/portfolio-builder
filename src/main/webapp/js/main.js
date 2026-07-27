/* =====================================================================
   Portfolio Builder System - Site-Wide JavaScript
   Mobile navigation, flash message dismissal, delete confirmations.
   File: js/main.js
   ===================================================================== */

document.addEventListener('DOMContentLoaded', function () {
    initMobileNav();
    initFlashDismiss();
    initConfirmForms();
    initColorPickerSync();
});

function initMobileNav() {
    var toggle = document.querySelector('.nav-toggle');
    var nav = document.querySelector('.site-nav');
    if (!toggle || !nav) {
        return;
    }
    toggle.addEventListener('click', function () {
        nav.classList.toggle('nav-open');
    });
}

function initFlashDismiss() {
    var dismissButtons = document.querySelectorAll('.alert-dismiss');
    dismissButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            var alertBox = button.closest('.alert');
            if (alertBox) {
                alertBox.remove();
            }
        });
    });
}

function initConfirmForms() {
    var confirmForms = document.querySelectorAll('form[data-confirm]');
    confirmForms.forEach(function (form) {
        form.addEventListener('submit', function (event) {
            var message = form.getAttribute('data-confirm') || 'Are you sure?';
            if (!window.confirm(message)) {
                event.preventDefault();
            }
        });
    });
}

function initColorPickerSync() {
    var colorInputs = document.querySelectorAll('input[type="color"][data-sync]');
    colorInputs.forEach(function (colorInput) {
        var targetId = colorInput.getAttribute('data-sync');
        var target = document.getElementById(targetId);
        if (!target) {
            return;
        }
        colorInput.addEventListener('input', function () {
            target.value = colorInput.value;
        });
        target.addEventListener('input', function () {
            if (/^#[0-9A-Fa-f]{6}$/.test(target.value)) {
                colorInput.value = target.value;
            }
        });
    });
}