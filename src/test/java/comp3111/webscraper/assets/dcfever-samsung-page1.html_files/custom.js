
/**
 * @fileoverview Custom functionality to apply throughout every adsize. This
 * has a dependency on common.js and utils.js
 */
var custom = (function() {

  /**
   * Classes which our JS hooks into. Add more class names as necessary.
   * @enum
   * @private
   */
  var elementClass_ = {
    item: 'js-item',
    itemPriceS: 'js-item-prices',
    itemPrice: 'js-item-price',
    itemSalePrice: 'js-item-saleprice',
    itemRegularPrice: 'js-item-regularprice',
    itemDetails1: 'js-item-details-1',
    itemDetails2: 'js-item-details-2',
    itemCta: 'js-item-cta'
  };

  /**
   * Initialization. Called from handleAdInitialized on each page.
   */
  function init() {
    utils.log('custom.init()');
    var data = common.getAdData();
    if (!data) return;

    // If you're using the swipe gallery to display feed items.
    initItemsUsingGallery_();

    // If you're NOT using the swipe gallery to display feed items.
    //initItemsWithoutGallery_();


  }

  /**
   * Find all items used in the swipe gallery and initialize custom behavior.
   * @private
   */
  function initItemsUsingGallery_() {
    var gallery = common.getGallery();

    // Apply settings to each item in the gallery
    var items = gallery.querySelectorAll('.' + elementClass_.item);
    for (var i = 0; i < items.length; i++) {
      var item = items[i];
      initItemDisplay_(item);
    }
  }

  /**
   * Find all items used outside the gallery and initialize custom behavior.
   * @private
   */
  function initItemsWithoutGallery_() {
    // Apply settings to each item
    var items = document.querySelectorAll('.' + elementClass_.item);
    for (var i = 0; i < items.length; i++) {
      var item = items[i];
      initItemDisplay_(item);
    }
  }

  /**
   * Set the display settings for each item.
   * Add any custom functionality you need applied on load.
   * @param {Element} item Item element.
   * @private
   */
  function initItemDisplay_(item) {

    // if you're using sales prices.
    setSalePricesDisplay_(item);

    // Set mouseout.
    itemMouseOut(item);
  }


  /**
   * Sets the 3 price elements to display correctly when using sales price.
   * Find your price elements and set into common functionality.
   * @param {Element} item Item element.
   * @private
   */
  function setSalePricesDisplay_(item) {
    // Get reference to each price element.
    var itemPrice = item.querySelector('.' + elementClass_.itemPrice);
    var itemSalePrice = item.querySelector('.' + elementClass_.itemSalePrice);
    var itemRegularPrice = item.querySelector('.' + elementClass_.itemRegularPrice);

    // Sets each item to display correct prices.
    common.displayCorrectPrices(itemPrice, itemSalePrice, itemRegularPrice);
  }

  /**
   * Custom Item Mouse Interactions. Add your own behavior.
   */

  /**
   * Custom Mouseover interaction functionality.
   * @param {Element} item
   */
  function itemMouseOver(item) {

  }

  /**
   * Custom Mouseout interaction functionality.
   * @param {Element} item
   */
  function itemMouseOut(item) {

  }

  /**
   * Custom Mouseover on CTA.
   * @param {Element} item
   */
  function itemCtaMouseover(event) {
    var cta = getItemCtaElement(event.target);
    if (cta) {
      var ctaOn = cta.children[1];
      if (ctaOn) utils.showElement(ctaOn, true);
    }
  }

  /**
   * Custom Mouseout on CTA.
   * @param {Element} item
   */
  function itemCtaMouseout(event) {
    var cta = getItemCtaElement(event.target);
    if (cta) {
      var ctaOn = cta.children[1];
      if (ctaOn) utils.showElement(ctaOn, false);
    }
  }

  /**
   * Find the item CTA element by traversing the item element.
   * @param {Element} el Element to check.
   * @return {Element}
   */
  function getItemCtaElement(el) {
    var cta = utils.closestElement(el, function(el) {
      return el.classList.contains(elementClass_.itemCta);
    });
    return cta;
  }

  return {
    init: init,
    itemMouseOver: itemMouseOver,
    itemMouseOut: itemMouseOut,
    itemCtaMouseover: itemCtaMouseover,
    itemCtaMouseout: itemCtaMouseout
  };

})();
