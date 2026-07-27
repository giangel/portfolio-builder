/* =====================================================================
   Portfolio Builder System - Storyline Builder JavaScript
   Drag-and-drop reordering, section visibility toggles, live preview.
   File: js/builder.js
   ===================================================================== */

document.addEventListener('DOMContentLoaded', function () {
    initSectionDragDrop();
    initEntryListDragDrop();
    initSectionToggles();
    initPreviewToggle();
});

/* -----------------------------------------------------------------
   Publish chapter: drag-and-drop section order, persisted through
   SectionOrderServlet at /portfolio/section-order (action=reorder).
   ----------------------------------------------------------------- */
function initSectionDragDrop() {
    var list = document.getElementById('section-order-list');
    if (!list) {
        return;
    }

    var draggedItem = null;

    list.querySelectorAll('.section-order-item').forEach(function (item) {
        item.setAttribute('draggable', 'true');

        item.addEventListener('dragstart', function () {
            draggedItem = item;
            item.classList.add('dragging');
        });

        item.addEventListener('dragend', function () {
            item.classList.remove('dragging');
            draggedItem = null;
            persistSectionOrder(list);
        });
    });

    list.addEventListener('dragover', function (event) {
        event.preventDefault();
        var afterElement = getDragAfterElement(list, event.clientY, '.section-order-item');
        if (!draggedItem) {
            return;
        }
        if (afterElement == null) {
            list.appendChild(draggedItem);
        } else {
            list.insertBefore(draggedItem, afterElement);
        }
    });
}

function persistSectionOrder(list) {
    var portfolioId = list.getAttribute('data-portfolio-id');
    var orderedIds = Array.from(list.querySelectorAll('.section-order-item'))
        .map(function (item) { return item.getAttribute('data-section-id'); })
        .join(',');

    var statusEl = document.getElementById('section-order-status');

    fetch(buildContextUrl('/portfolio/section-order'), {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: 'action=reorder&portfolioId=' + encodeURIComponent(portfolioId) + '&orderedIds=' + encodeURIComponent(orderedIds)
    })
        .then(function (response) { return response.json(); })
        .then(function (data) {
            if (!statusEl) {
                return;
            }
            if (data.success) {
                statusEl.textContent = 'Order saved.';
                statusEl.className = 'form-hint';
            } else {
                statusEl.textContent = data.message || 'Could not save order.';
                statusEl.className = 'form-hint';
                statusEl.style.color = 'var(--color-error)';
            }
        })
        .catch(function () {
            if (statusEl) {
                statusEl.textContent = 'Could not save order, check your connection.';
                statusEl.style.color = 'var(--color-error)';
            }
        });
}

/* -----------------------------------------------------------------
   Any content chapter's entry list (skills, experience, education,
   projects, certifications, services). Reordering here submits a
   normal hidden form back to that chapter's own Servlet with
   action=reorder, rather than fetch, so it works identically to the
   section list but reuses each chapter's existing POST handler.
   ----------------------------------------------------------------- */
function initEntryListDragDrop() {
    var lists = document.querySelectorAll('.entry-list[data-reorder-url]');

    lists.forEach(function (list) {
        var draggedItem = null;

        list.querySelectorAll('.entry-item').forEach(function (item) {
            item.setAttribute('draggable', 'true');

            item.addEventListener('dragstart', function () {
                draggedItem = item;
                item.classList.add('dragging');
            });

            item.addEventListener('dragend', function () {
                item.classList.remove('dragging');
                draggedItem = null;
                submitEntryOrder(list);
            });
        });

        list.addEventListener('dragover', function (event) {
            event.preventDefault();
            var afterElement = getDragAfterElement(list, event.clientY, '.entry-item');
            if (!draggedItem) {
                return;
            }
            if (afterElement == null) {
                list.appendChild(draggedItem);
            } else {
                list.insertBefore(draggedItem, afterElement);
            }
        });
    });
}

function submitEntryOrder(list) {
    var reorderUrl = list.getAttribute('data-reorder-url');
    var portfolioId = list.getAttribute('data-portfolio-id');
    var orderedIds = Array.from(list.querySelectorAll('.entry-item'))
        .map(function (item) { return item.getAttribute('data-entry-id'); })
        .join(',');

    var form = document.createElement('form');
    form.method = 'POST';
    form.action = buildContextUrl(reorderUrl);
    form.style.display = 'none';

    appendHiddenField(form, 'action', 'reorder');
    appendHiddenField(form, 'portfolioId', portfolioId);
    appendHiddenField(form, 'orderedIds', orderedIds);

    document.body.appendChild(form);
    form.submit();
}

function appendHiddenField(form, name, value) {
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = name;
    input.value = value;
    form.appendChild(input);
}

/* -----------------------------------------------------------------
   Shared drag helper: finds the element a dragged item should be
   inserted before, based on vertical mouse position.
   ----------------------------------------------------------------- */
function getDragAfterElement(container, y, itemSelector) {
    var items = Array.from(container.querySelectorAll(itemSelector + ':not(.dragging)'));

    return items.reduce(function (closest, child) {
        var box = child.getBoundingClientRect();
        var offset = y - box.top - box.height / 2;
        if (offset < 0 && offset > closest.offset) {
            return { offset: offset, element: child };
        }
        return closest;
    }, { offset: Number.NEGATIVE_INFINITY, element: null }).element;
}

/* -----------------------------------------------------------------
   Publish chapter: section visibility toggle switches submit
   immediately on change through a small auto-submit form per row.
   ----------------------------------------------------------------- */
function initSectionToggles() {
    var toggles = document.querySelectorAll('.section-visibility-toggle');
    toggles.forEach(function (toggle) {
        toggle.addEventListener('change', function () {
            var form = toggle.closest('form');
            if (form) {
                var enabledField = form.querySelector('input[name="enabled"]');
                if (enabledField) {
                    enabledField.value = toggle.checked ? 'true' : 'false';
                }
                form.submit();
            }
        });
    });
}

/* -----------------------------------------------------------------
   Live preview toggle, shows or refreshes an iframe pointed at
   PreviewPortfolioServlet (/portfolio/preview) from Phase 10,
   matching the Storyline concept's optional focus/preview modes.
   ----------------------------------------------------------------- */
function initPreviewToggle() {
    var button = document.getElementById('toggle-preview-button');
    var wrapper = document.getElementById('preview-frame-wrapper');
    var iframe = document.getElementById('preview-frame');

    if (!button || !wrapper || !iframe) {
        return;
    }

    button.addEventListener('click', function () {
        var isOpen = wrapper.classList.toggle('open');
        button.textContent = isOpen ? 'Hide preview' : 'Show live preview';
        if (isOpen) {
            var src = iframe.getAttribute('data-src');
            iframe.src = src + (src.indexOf('?') > -1 ? '&' : '?') + 'ts=' + Date.now();
        }
    });
}

/* -----------------------------------------------------------------
   Builds a context-relative URL. Reads the context path from a
   data attribute on <body>, set once in the layout template, since
   plain JavaScript has no server-side request object to ask.
   ----------------------------------------------------------------- */
function buildContextUrl(path) {
    var contextPath = document.body.getAttribute('data-context-path') || '';
    if (!path.startsWith('/')) {
        path = '/' + path;
    }
    return contextPath + path;
}