function stringlist_add_item(dummy_item_button)
{
    let dummy_item = $(dummy_item_button).closest('.stringlist-dummy-item');
    let root = dummy_item.closest('.stringlist');
    let items = root.find('.stringlist-items');
    let new_item = dummy_item.clone();

    // Change `+` into `-`.
    let button = new_item.find('button');
    button.text('-');
    button.attr('onclick', 'javascript: $(this).parent().remove();');

    let input = new_item.find('input');
    input.attr('disabled', null);

    // Remove dummy-item class.
    new_item.removeClass('stringlist-dummy-item');

    // Append to items.
    items.append(new_item);

    // Renumber items to keep Spring happy.
    stringlist_renumber(items);
}

function stringlist_delete_item(item_button)
{
    let item = $(item_button).closest('.stringlist-item');
    let items = item.closest('.stringlist-items');

    // Delete item.
    item.remove();

    // Renumber items to keep Spring happy.
    stringlist_renumber(items);
}

function stringlist_renumber(items)
{
    let root = items.closest('.stringlist');
    let dummy_item_input = root.find('.stringlist-dummy-item input');
    let name = dummy_item_input.attr('name');
    items.find('.stringlist-item').each(function(index, item) {
        $(item).find('input').attr('name', name + "[" + index + "]");
    });
}