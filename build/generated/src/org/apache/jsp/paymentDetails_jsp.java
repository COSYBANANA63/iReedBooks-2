package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class paymentDetails_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_forEach_var_items;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_set_var_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_forEach_var_end_begin;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_formatNumber_value_pattern_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_c_forEach_var_items = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_c_set_var_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_c_forEach_var_end_begin = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_fmt_formatNumber_value_pattern_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_forEach_var_items.release();
    _jspx_tagPool_c_set_var_value_nobody.release();
    _jspx_tagPool_c_forEach_var_end_begin.release();
    _jspx_tagPool_fmt_formatNumber_value_pattern_nobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <title>Payment Details - iReedBooks</title>\n");
      out.write("    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css\">\n");
      out.write("    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css\">\n");
      out.write("    <style>\n");
      out.write("        .card-icon {\n");
      out.write("            font-size: 1.5rem;\n");
      out.write("            margin-left: 10px;\n");
      out.write("            vertical-align: middle;\n");
      out.write("        }\n");
      out.write("        .invalid-feedback {\n");
      out.write("            display: none;\n");
      out.write("        }\n");
      out.write("        input.is-invalid + .invalid-feedback {\n");
      out.write("            display: block;\n");
      out.write("        }\n");
      out.write("        .card-brand {\n");
      out.write("            display: inline-block;\n");
      out.write("            padding: 2px 8px;\n");
      out.write("            border-radius: 4px;\n");
      out.write("            margin-left: 8px;\n");
      out.write("            font-size: 0.8rem;\n");
      out.write("            font-weight: bold;\n");
      out.write("        }\n");
      out.write("        .card-brand.visa {\n");
      out.write("            background-color: #1A1F71;\n");
      out.write("            color: white;\n");
      out.write("        }\n");
      out.write("        .card-brand.mastercard {\n");
      out.write("            background-color: #EB001B;\n");
      out.write("            color: white;\n");
      out.write("        }\n");
      out.write("        .card-brand.amex {\n");
      out.write("            background-color: #006FCF;\n");
      out.write("            color: white;\n");
      out.write("        }\n");
      out.write("        .card-brand.discover {\n");
      out.write("            background-color: #FF6600;\n");
      out.write("            color: white;\n");
      out.write("        }\n");
      out.write("        .card-brand.diners {\n");
      out.write("            background-color: #0079BE;\n");
      out.write("            color: white;\n");
      out.write("        }\n");
      out.write("        .card-brand.jcb {\n");
      out.write("            background-color: #00873C;\n");
      out.write("            color: white;\n");
      out.write("        }\n");
      out.write("        .card-brand.verve {\n");
      out.write("            background-color: #3399cc;\n");
      out.write("            color: white;\n");
      out.write("        }\n");
      out.write("    </style>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "header.jsp", out, false);
      out.write("\n");
      out.write("    \n");
      out.write("    <div class=\"container mt-4\">\n");
      out.write("        <nav aria-label=\"breadcrumb\">\n");
      out.write("            <ol class=\"breadcrumb\">\n");
      out.write("                <li class=\"breadcrumb-item\"><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/\">Home</a></li>\n");
      out.write("                <li class=\"breadcrumb-item\"><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/cart\">Shopping Cart</a></li>\n");
      out.write("                <li class=\"breadcrumb-item active\" aria-current=\"page\">Payment Details</li>\n");
      out.write("            </ol>\n");
      out.write("        </nav>\n");
      out.write("        \n");
      out.write("        <div class=\"row\">\n");
      out.write("            <!-- Order Summary -->\n");
      out.write("            <div class=\"col-md-4 order-md-2 mb-4\">\n");
      out.write("                <div class=\"card\">\n");
      out.write("                    <div class=\"card-header\">\n");
      out.write("                        <h5 class=\"card-title mb-0\">Order Summary</h5>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"card-body\">\n");
      out.write("                        <ul class=\"list-group list-group-flush\">\n");
      out.write("                            ");
      if (_jspx_meth_c_forEach_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("                            <li class=\"list-group-item d-flex justify-content-between\">\n");
      out.write("                                <span>Total</span>\n");
      out.write("                                <strong>$");
      if (_jspx_meth_fmt_formatNumber_1(_jspx_page_context))
        return;
      out.write("</strong>\n");
      out.write("                            </li>\n");
      out.write("                        </ul>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("                \n");
      out.write("                <!-- Payment success/failure toggle switch -->\n");
      out.write("                <div class=\"card mt-3\">\n");
      out.write("                    <div class=\"card-body\">\n");
      out.write("                        <div class=\"form-check form-switch\">\n");
      out.write("                            <input class=\"form-check-input\" type=\"checkbox\" id=\"paymentSuccessToggle\" checked>\n");
      out.write("                            <label class=\"form-check-label\" for=\"paymentSuccessToggle\">Simulate Successful Payment</label>\n");
      out.write("                        </div>\n");
      out.write("                        <small class=\"text-muted\">For demonstration purposes only</small>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("            \n");
      out.write("            <!-- Payment Form -->\n");
      out.write("            <div class=\"col-md-8 order-md-1\">\n");
      out.write("                <div class=\"card\">\n");
      out.write("                    <div class=\"card-header\">\n");
      out.write("                        <h5 class=\"card-title mb-0\">Payment Details</h5>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"card-body\">\n");
      out.write("                        <form id=\"paymentForm\" action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/cart/process-payment\" method=\"post\" novalidate>\n");
      out.write("                            <!-- Billing Information -->\n");
      out.write("                            <h6 class=\"mb-3\">Billing Information</h6>\n");
      out.write("                            <div class=\"row g-3 mb-4\">\n");
      out.write("                                <div class=\"col-md-6\">\n");
      out.write("                                    <label for=\"firstName\" class=\"form-label\">First name</label>\n");
      out.write("                                    <input type=\"text\" class=\"form-control\" id=\"firstName\" name=\"firstName\" required\n");
      out.write("                                           pattern=\"[A-Za-z -']+\" minlength=\"2\" maxlength=\"50\">\n");
      out.write("                                    <div class=\"invalid-feedback\">\n");
      out.write("                                        Please enter a valid first name.\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"col-md-6\">\n");
      out.write("                                    <label for=\"lastName\" class=\"form-label\">Last name</label>\n");
      out.write("                                    <input type=\"text\" class=\"form-control\" id=\"lastName\" name=\"lastName\" required\n");
      out.write("                                           pattern=\"[A-Za-z -']+\" minlength=\"2\" maxlength=\"50\">\n");
      out.write("                                    <div class=\"invalid-feedback\">\n");
      out.write("                                        Please enter a valid last name.\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"col-12\">\n");
      out.write("                                    <label for=\"address\" class=\"form-label\">Address</label>\n");
      out.write("                                    <input type=\"text\" class=\"form-control\" id=\"address\" name=\"address\" required\n");
      out.write("                                           minlength=\"5\" maxlength=\"100\">\n");
      out.write("                                    <div class=\"invalid-feedback\">\n");
      out.write("                                        Please enter your shipping address.\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"col-md-5\">\n");
      out.write("                                    <label for=\"city\" class=\"form-label\">City</label>\n");
      out.write("                                    <input type=\"text\" class=\"form-control\" id=\"city\" name=\"city\" required\n");
      out.write("                                           pattern=\"[A-Za-z -']+\" minlength=\"2\" maxlength=\"50\">\n");
      out.write("                                    <div class=\"invalid-feedback\">\n");
      out.write("                                        Please enter a valid city.\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"col-md-4\">\n");
      out.write("                                    <label for=\"state\" class=\"form-label\">State</label>\n");
      out.write("                                    <input type=\"text\" class=\"form-control\" id=\"state\" name=\"state\" required\n");
      out.write("                                           pattern=\"[A-Za-z -]+\" minlength=\"2\" maxlength=\"50\">\n");
      out.write("                                    <div class=\"invalid-feedback\">\n");
      out.write("                                        Please enter a valid state.\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"col-md-3\">\n");
      out.write("                                    <label for=\"zipCode\" class=\"form-label\">Zip</label>\n");
      out.write("                                    <input type=\"text\" class=\"form-control\" id=\"zipCode\" name=\"zipCode\" required\n");
      out.write("                                           pattern=\"^\\d{5}(-\\d{4})?$\">\n");
      out.write("                                    <div class=\"invalid-feedback\">\n");
      out.write("                                        Please enter a valid zip code (e.g., 12345 or 12345-6789).\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                            </div>\n");
      out.write("                            \n");
      out.write("                            <!-- Card Information -->\n");
      out.write("                            <h6 class=\"mb-3\">Card Information</h6>\n");
      out.write("                            <div class=\"row g-3\">\n");
      out.write("                                <div class=\"col-md-6\">\n");
      out.write("                                    <label for=\"cardName\" class=\"form-label\">Name on card</label>\n");
      out.write("                                    <input type=\"text\" class=\"form-control\" id=\"cardName\" name=\"cardName\" required\n");
      out.write("                                           pattern=\"[A-Za-z -']+\" minlength=\"2\" maxlength=\"100\">\n");
      out.write("                                    <div class=\"invalid-feedback\">\n");
      out.write("                                        Please enter the name as it appears on your card.\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"col-md-6\">\n");
      out.write("                                    <label for=\"cardNumber\" class=\"form-label\">Card number</label>\n");
      out.write("                                    <div class=\"input-group\">\n");
      out.write("                                        <input type=\"text\" class=\"form-control\" id=\"cardNumber\" name=\"cardNumber\" \n");
      out.write("                                               placeholder=\"XXXX XXXX XXXX XXXX\" required maxlength=\"19\">\n");
      out.write("                                        <span class=\"input-group-text\" id=\"cardBrandDisplay\"></span>\n");
      out.write("                                    </div>\n");
      out.write("                                    <div class=\"invalid-feedback\">\n");
      out.write("                                        Please enter a valid credit card number.\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"col-md-4\">\n");
      out.write("                                    <label for=\"expiryMonth\" class=\"form-label\">Expiry month</label>\n");
      out.write("                                    <select class=\"form-select\" id=\"expiryMonth\" name=\"expiryMonth\" required>\n");
      out.write("                                        <option value=\"\">Choose...</option>\n");
      out.write("                                        ");
      if (_jspx_meth_c_forEach_1(_jspx_page_context))
        return;
      out.write("\n");
      out.write("                                    </select>\n");
      out.write("                                    <div class=\"invalid-feedback\">\n");
      out.write("                                        Please select an expiry month.\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"col-md-4\">\n");
      out.write("                                    <label for=\"expiryYear\" class=\"form-label\">Expiry year</label>\n");
      out.write("                                    <select class=\"form-select\" id=\"expiryYear\" name=\"expiryYear\" required>\n");
      out.write("                                        <option value=\"\">Choose...</option>\n");
      out.write("                                        ");
      //  c:set
      org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
      _jspx_th_c_set_0.setPageContext(_jspx_page_context);
      _jspx_th_c_set_0.setParent(null);
      _jspx_th_c_set_0.setVar("currentYear");
      _jspx_th_c_set_0.setValue( java.time.Year.now().getValue() );
      int _jspx_eval_c_set_0 = _jspx_th_c_set_0.doStartTag();
      if (_jspx_th_c_set_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
        return;
      }
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
      out.write("\n");
      out.write("                                        ");
      if (_jspx_meth_c_forEach_2(_jspx_page_context))
        return;
      out.write("\n");
      out.write("                                    </select>\n");
      out.write("                                    <div class=\"invalid-feedback\">\n");
      out.write("                                        Please select an expiry year.\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"col-md-4\">\n");
      out.write("                                    <label for=\"cvv\" class=\"form-label\">CVV</label>\n");
      out.write("                                    <input type=\"text\" class=\"form-control\" id=\"cvv\" name=\"cvv\" required\n");
      out.write("                                           placeholder=\"XXX\" maxlength=\"4\" pattern=\"^\\d{3,4}$\">\n");
      out.write("                                    <div class=\"invalid-feedback\">\n");
      out.write("                                        Please enter a valid CVV (3-4 digits).\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                            </div>\n");
      out.write("                            \n");
      out.write("                            <input type=\"hidden\" id=\"paymentSuccess\" name=\"paymentSuccess\" value=\"true\">\n");
      out.write("                            \n");
      out.write("                            <hr class=\"my-4\">\n");
      out.write("                            \n");
      out.write("                            <div class=\"d-flex justify-content-between\">\n");
      out.write("                                <a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/cart\" class=\"btn btn-outline-secondary\">\n");
      out.write("                                    <i class=\"bi bi-arrow-left\"></i> Back to Cart\n");
      out.write("                                </a>\n");
      out.write("                                <button class=\"btn btn-primary\" type=\"submit\" id=\"submitBtn\">\n");
      out.write("                                    <i class=\"bi bi-credit-card me-2\"></i> Complete Payment\n");
      out.write("                                </button>\n");
      out.write("                            </div>\n");
      out.write("                        </form>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    \n");
      out.write("    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "footer.jsp", out, false);
      out.write("\n");
      out.write("    \n");
      out.write("    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js\"></script>\n");
      out.write("    <script>\n");
      out.write("        document.addEventListener('DOMContentLoaded', function() {\n");
      out.write("            const toggleSwitch = document.getElementById('paymentSuccessToggle');\n");
      out.write("            const hiddenInput = document.getElementById('paymentSuccess');\n");
      out.write("            const form = document.getElementById('paymentForm');\n");
      out.write("            const submitBtn = document.getElementById('submitBtn');\n");
      out.write("            const cardNumberInput = document.getElementById('cardNumber');\n");
      out.write("            const cardBrandDisplay = document.getElementById('cardBrandDisplay');\n");
      out.write("            const cvvInput = document.getElementById('cvv');\n");
      out.write("            const expiryMonth = document.getElementById('expiryMonth');\n");
      out.write("            const expiryYear = document.getElementById('expiryYear');\n");
      out.write("            \n");
      out.write("            // Card type regex patterns\n");
      out.write("            const cardPatterns = {\n");
      out.write("                visa: /^4[0-9]{12}(?:[0-9]{3})?$/,\n");
      out.write("                mastercard: /^5[1-5][0-9]{14}$|^2(?:2(?:2[1-9]|[3-9][0-9])|[3-6][0-9][0-9]|7(?:[01][0-9]|20))[0-9]{12}$/,\n");
      out.write("                amex: /^3[47][0-9]{13}$/,\n");
      out.write("                discover: /^6(?:011|5[0-9]{2})[0-9]{12}$/,\n");
      out.write("                diners: /^3(?:0[0-5]|[68][0-9])[0-9]{11}$/,\n");
      out.write("                jcb: /^(?:2131|1800|35[0-9]{3})[0-9]{11}$/,\n");
      out.write("                verve: /^506(?:099|1[0-9][0-9]|2[0-4][0-9]|25[0-5])[0-9]{10}$|^650[0-9]{13}$/\n");
      out.write("            };\n");
      out.write("            \n");
      out.write("            // Format card number with spaces\n");
      out.write("            cardNumberInput.addEventListener('input', function(e) {\n");
      out.write("                // Remove non-digits\n");
      out.write("                let value = this.value.replace(/\\D/g, '');\n");
      out.write("                \n");
      out.write("                // Add spaces after every 4 digits\n");
      out.write("                if (value.length > 0) {\n");
      out.write("                    value = value.match(new RegExp('.{1,4}', 'g')).join(' ');\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                // Update input value\n");
      out.write("                this.value = value;\n");
      out.write("                \n");
      out.write("                // Detect and display card type\n");
      out.write("                detectCardType(this.value.replace(/\\s/g, ''));\n");
      out.write("                \n");
      out.write("                // Set CVV max length based on card type (AMEX = 4, others = 3)\n");
      out.write("                const isAmex = this.value.replace(/\\s/g, '').match(cardPatterns.amex);\n");
      out.write("                cvvInput.setAttribute('maxlength', isAmex ? '4' : '3');\n");
      out.write("                cvvInput.setAttribute('pattern', isAmex ? '^\\\\d{4}$' : '^\\\\d{3}$');\n");
      out.write("                \n");
      out.write("                // Validate the field\n");
      out.write("                validateField(this);\n");
      out.write("            });\n");
      out.write("            \n");
      out.write("            // Detect card type and display brand\n");
      out.write("            function detectCardType(cardNumber) {\n");
      out.write("                // Remove any existing brand classes\n");
      out.write("                cardBrandDisplay.className = 'input-group-text';\n");
      out.write("                cardBrandDisplay.textContent = '';\n");
      out.write("                \n");
      out.write("                if (!cardNumber) return;\n");
      out.write("                \n");
      out.write("                for (const [brand, pattern] of Object.entries(cardPatterns)) {\n");
      out.write("                    if (pattern.test(cardNumber)) {\n");
      out.write("                        const brandSpan = document.createElement('span');\n");
      out.write("                        brandSpan.className = `card-brand ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${brand}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("`;\n");
      out.write("                        brandSpan.textContent = brand.charAt(0).toUpperCase() + brand.slice(1);\n");
      out.write("                        cardBrandDisplay.appendChild(brandSpan);\n");
      out.write("                        return;\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            // Validate card number using Luhn algorithm\n");
      out.write("            function validateCardNumber(cardNumber) {\n");
      out.write("                // Remove spaces and non-digits\n");
      out.write("                cardNumber = cardNumber.replace(/\\D/g, '');\n");
      out.write("                \n");
      out.write("                if (cardNumber.length < 13 || cardNumber.length > 19) return false;\n");
      out.write("                \n");
      out.write("                let sum = 0;\n");
      out.write("                let shouldDouble = false;\n");
      out.write("                \n");
      out.write("                // Loop through values starting from the right\n");
      out.write("                for (let i = cardNumber.length - 1; i >= 0; i--) {\n");
      out.write("                    let digit = parseInt(cardNumber.charAt(i));\n");
      out.write("                    \n");
      out.write("                    if (shouldDouble) {\n");
      out.write("                        digit *= 2;\n");
      out.write("                        if (digit > 9) digit -= 9;\n");
      out.write("                    }\n");
      out.write("                    \n");
      out.write("                    sum += digit;\n");
      out.write("                    shouldDouble = !shouldDouble;\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                return sum % 10 === 0;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            // Validate credit card expiry date\n");
      out.write("            function validateExpiry() {\n");
      out.write("                const month = parseInt(expiryMonth.value);\n");
      out.write("                const year = parseInt(expiryYear.value);\n");
      out.write("                \n");
      out.write("                if (!month || !year) return false;\n");
      out.write("                \n");
      out.write("                const expiryDate = new Date(year, month);\n");
      out.write("                const currentDate = new Date();\n");
      out.write("                currentDate.setDate(1); // First day of current month\n");
      out.write("                \n");
      out.write("                return expiryDate > currentDate;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            // Field validation function\n");
      out.write("            function validateField(field) {\n");
      out.write("                const isValid = field.checkValidity();\n");
      out.write("                \n");
      out.write("                // Special validation for card number\n");
      out.write("                if (field.id === 'cardNumber') {\n");
      out.write("                    const cardNumber = field.value.replace(/\\s/g, '');\n");
      out.write("                    const matchesPattern = Object.values(cardPatterns).some(pattern => pattern.test(cardNumber));\n");
      out.write("                    const passesLuhn = validateCardNumber(cardNumber);\n");
      out.write("                    \n");
      out.write("                    if (!matchesPattern || !passesLuhn) {\n");
      out.write("                        field.setCustomValidity('Please enter a valid credit card number');\n");
      out.write("                        field.classList.add('is-invalid');\n");
      out.write("                        return false;\n");
      out.write("                    } else {\n");
      out.write("                        field.setCustomValidity('');\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                // Special validation for CVV\n");
      out.write("                if (field.id === 'cvv') {\n");
      out.write("                    const cardNumber = cardNumberInput.value.replace(/\\s/g, '');\n");
      out.write("                    const isAmex = cardPatterns.amex.test(cardNumber);\n");
      out.write("                    const cvvLength = field.value.length;\n");
      out.write("                    \n");
      out.write("                    if ((isAmex && cvvLength !== 4) || (!isAmex && cvvLength !== 3)) {\n");
      out.write("                        field.setCustomValidity('Please enter a valid CVV code');\n");
      out.write("                        field.classList.add('is-invalid');\n");
      out.write("                        return false;\n");
      out.write("                    } else {\n");
      out.write("                        field.setCustomValidity('');\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                // Update field styling based on validation\n");
      out.write("                if (isValid) {\n");
      out.write("                    field.classList.remove('is-invalid');\n");
      out.write("                    field.classList.add('is-valid');\n");
      out.write("                } else {\n");
      out.write("                    field.classList.remove('is-valid');\n");
      out.write("                    field.classList.add('is-invalid');\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                return isValid;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            // Add input validation to all required fields\n");
      out.write("            form.querySelectorAll('input[required], select[required]').forEach(field => {\n");
      out.write("                field.addEventListener('blur', () => validateField(field));\n");
      out.write("                field.addEventListener('input', () => validateField(field));\n");
      out.write("                field.addEventListener('change', () => validateField(field));\n");
      out.write("            });\n");
      out.write("            \n");
      out.write("            // Payment success/failure toggle\n");
      out.write("            toggleSwitch.addEventListener('change', function() {\n");
      out.write("                hiddenInput.value = this.checked ? 'true' : 'false';\n");
      out.write("            });\n");
      out.write("            \n");
      out.write("            // Form submission\n");
      out.write("            form.addEventListener('submit', function(e) {\n");
      out.write("                e.preventDefault();\n");
      out.write("                \n");
      out.write("                let isFormValid = true;\n");
      out.write("                \n");
      out.write("                // Validate all fields\n");
      out.write("                form.querySelectorAll('input[required], select[required]').forEach(field => {\n");
      out.write("                    if (!validateField(field)) {\n");
      out.write("                        isFormValid = false;\n");
      out.write("                    }\n");
      out.write("                });\n");
      out.write("                \n");
      out.write("                // Validate expiry date\n");
      out.write("                if (!validateExpiry()) {\n");
      out.write("                    expiryMonth.classList.add('is-invalid');\n");
      out.write("                    expiryYear.classList.add('is-invalid');\n");
      out.write("                    isFormValid = false;\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                if (isFormValid) {\n");
      out.write("                    form.submit();\n");
      out.write("                } else {\n");
      out.write("                    // Scroll to the first invalid field\n");
      out.write("                    const firstInvalid = form.querySelector('.is-invalid');\n");
      out.write("                    if (firstInvalid) {\n");
      out.write("                        firstInvalid.focus();\n");
      out.write("                        firstInvalid.scrollIntoView({ behavior: 'smooth', block: 'center' });\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("            });\n");
      out.write("            \n");
      out.write("            // Format CVV to numbers only\n");
      out.write("            cvvInput.addEventListener('input', function() {\n");
      out.write("                this.value = this.value.replace(/\\D/g, '');\n");
      out.write("                validateField(this);\n");
      out.write("            });\n");
      out.write("        });\n");
      out.write("    </script>\n");
      out.write("</body>\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_forEach_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_forEach_0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _jspx_tagPool_c_forEach_var_items.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_forEach_0.setPageContext(_jspx_page_context);
    _jspx_th_c_forEach_0.setParent(null);
    _jspx_th_c_forEach_0.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${cartItems}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    _jspx_th_c_forEach_0.setVar("item");
    int[] _jspx_push_body_count_c_forEach_0 = new int[] { 0 };
    try {
      int _jspx_eval_c_forEach_0 = _jspx_th_c_forEach_0.doStartTag();
      if (_jspx_eval_c_forEach_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("                                <li class=\"list-group-item d-flex justify-content-between lh-sm\">\n");
          out.write("                                    <div>\n");
          out.write("                                        <h6 class=\"my-0\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${item.title}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write("</h6>\n");
          out.write("                                        <small class=\"text-muted\">Qty: ");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${item.quantity}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write("</small>\n");
          out.write("                                    </div>\n");
          out.write("                                    <span class=\"text-muted\">$");
          if (_jspx_meth_fmt_formatNumber_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_c_forEach_0, _jspx_page_context, _jspx_push_body_count_c_forEach_0))
            return true;
          out.write("</span>\n");
          out.write("                                </li>\n");
          out.write("                            ");
          int evalDoAfterBody = _jspx_th_c_forEach_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_forEach_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_forEach_0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_forEach_0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_forEach_0.doFinally();
      _jspx_tagPool_c_forEach_var_items.reuse(_jspx_th_c_forEach_0);
    }
    return false;
  }

  private boolean _jspx_meth_fmt_formatNumber_0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_forEach_0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_forEach_0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:formatNumber
    org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag _jspx_th_fmt_formatNumber_0 = (org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag) _jspx_tagPool_fmt_formatNumber_value_pattern_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag.class);
    _jspx_th_fmt_formatNumber_0.setPageContext(_jspx_page_context);
    _jspx_th_fmt_formatNumber_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_forEach_0);
    _jspx_th_fmt_formatNumber_0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${item.total}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    _jspx_th_fmt_formatNumber_0.setPattern("#,##0.00");
    int _jspx_eval_fmt_formatNumber_0 = _jspx_th_fmt_formatNumber_0.doStartTag();
    if (_jspx_th_fmt_formatNumber_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_formatNumber_value_pattern_nobody.reuse(_jspx_th_fmt_formatNumber_0);
      return true;
    }
    _jspx_tagPool_fmt_formatNumber_value_pattern_nobody.reuse(_jspx_th_fmt_formatNumber_0);
    return false;
  }

  private boolean _jspx_meth_fmt_formatNumber_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:formatNumber
    org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag _jspx_th_fmt_formatNumber_1 = (org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag) _jspx_tagPool_fmt_formatNumber_value_pattern_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.FormatNumberTag.class);
    _jspx_th_fmt_formatNumber_1.setPageContext(_jspx_page_context);
    _jspx_th_fmt_formatNumber_1.setParent(null);
    _jspx_th_fmt_formatNumber_1.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${total}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    _jspx_th_fmt_formatNumber_1.setPattern("#,##0.00");
    int _jspx_eval_fmt_formatNumber_1 = _jspx_th_fmt_formatNumber_1.doStartTag();
    if (_jspx_th_fmt_formatNumber_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_formatNumber_value_pattern_nobody.reuse(_jspx_th_fmt_formatNumber_1);
      return true;
    }
    _jspx_tagPool_fmt_formatNumber_value_pattern_nobody.reuse(_jspx_th_fmt_formatNumber_1);
    return false;
  }

  private boolean _jspx_meth_c_forEach_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_forEach_1 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _jspx_tagPool_c_forEach_var_end_begin.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_forEach_1.setPageContext(_jspx_page_context);
    _jspx_th_c_forEach_1.setParent(null);
    _jspx_th_c_forEach_1.setBegin(1);
    _jspx_th_c_forEach_1.setEnd(12);
    _jspx_th_c_forEach_1.setVar("month");
    int[] _jspx_push_body_count_c_forEach_1 = new int[] { 0 };
    try {
      int _jspx_eval_c_forEach_1 = _jspx_th_c_forEach_1.doStartTag();
      if (_jspx_eval_c_forEach_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("                                            <option value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${month < 10 ? '0' : ''}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${month}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${month < 10 ? '0' : ''}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${month}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write("</option>\n");
          out.write("                                        ");
          int evalDoAfterBody = _jspx_th_c_forEach_1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_forEach_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_forEach_1[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_forEach_1.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_forEach_1.doFinally();
      _jspx_tagPool_c_forEach_var_end_begin.reuse(_jspx_th_c_forEach_1);
    }
    return false;
  }

  private boolean _jspx_meth_c_forEach_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_forEach_2 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _jspx_tagPool_c_forEach_var_end_begin.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_forEach_2.setPageContext(_jspx_page_context);
    _jspx_th_c_forEach_2.setParent(null);
    _jspx_th_c_forEach_2.setBegin(((java.lang.Integer) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${currentYear}", java.lang.Integer.class, (PageContext)_jspx_page_context, null)).intValue());
    _jspx_th_c_forEach_2.setEnd(((java.lang.Integer) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${currentYear + 10}", java.lang.Integer.class, (PageContext)_jspx_page_context, null)).intValue());
    _jspx_th_c_forEach_2.setVar("year");
    int[] _jspx_push_body_count_c_forEach_2 = new int[] { 0 };
    try {
      int _jspx_eval_c_forEach_2 = _jspx_th_c_forEach_2.doStartTag();
      if (_jspx_eval_c_forEach_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("                                            <option value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${year}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${year}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write("</option>\n");
          out.write("                                        ");
          int evalDoAfterBody = _jspx_th_c_forEach_2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_forEach_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_forEach_2[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_forEach_2.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_forEach_2.doFinally();
      _jspx_tagPool_c_forEach_var_end_begin.reuse(_jspx_th_c_forEach_2);
    }
    return false;
  }
}
