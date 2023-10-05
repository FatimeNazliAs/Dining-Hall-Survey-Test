package org.primefaces.freya.view;

import org.primefaces.PrimeFaces;
import org.primefaces.freya.domain.Product;
import org.primefaces.freya.service.FreyaOrderService;
import org.primefaces.freya.service.FreyaProductService;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;


@ManagedBean(name = "crudDemoView")
@ViewScoped
public class CrudDemoView implements Serializable
{

    private List<Product> products;

    private Product selectedProduct;

    private List<Product> selectedProducts;

    @ManagedProperty("#{freyaProductService}")
    private FreyaProductService freyaProductService;

    @ManagedProperty("#{freyaOrderService}")
    private FreyaOrderService freyaOrderService;

    @PostConstruct
    public void init()
    {
        this.products = this.freyaProductService.getProducts();
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public Product getSelectedProduct()
    {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct)
    {
        this.selectedProduct = selectedProduct;
    }

    public List<Product> getSelectedProducts()
    {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Product> selectedProducts)
    {
        this.selectedProducts = selectedProducts;
    }

    public void openNew()
    {
        this.selectedProduct = new Product();
    }

    public void saveProduct()
    {
        if (this.selectedProduct.getCode() == null)
        {
            this.selectedProduct.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            this.products.add(this.selectedProduct);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Added"));
        } else
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public void deleteProduct()
    {
        this.products.remove(this.selectedProduct);
        this.selectedProduct = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public String getDeleteButtonMessage()
    {
        if (hasSelectedProducts())
        {
            int size = this.selectedProducts.size();
            return size > 1 ? size + " products selected" : "1 product selected";
        }

        return "Delete";
    }

    public boolean hasSelectedProducts()
    {
        return this.selectedProducts != null && !this.selectedProducts.isEmpty();
    }

    public void deleteSelectedProducts()
    {
        this.products.removeAll(this.selectedProducts);
        this.selectedProducts = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Products Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public void onRowToggle(ToggleEvent event)
    {
        if (event.getVisibility() == Visibility.VISIBLE)
        {
            Product product = (Product) event.getData();
            if (product.getOrders() == null)
            {
                product.setOrders(freyaOrderService.getOrders((int) (Math.random() * 10)));
            }
        }
    }

    public void setProductService(FreyaProductService freyaProductService)
    {
        this.freyaProductService = freyaProductService;
    }

    public void setFreyaOrderService(FreyaOrderService freyaOrderService)
    {
        this.freyaOrderService = freyaOrderService;
    }

    public FreyaProductService getFreyaProductService()
    {
        return freyaProductService;
    }

    public void setFreyaProductService(FreyaProductService freyaProductService)
    {
        this.freyaProductService = freyaProductService;
    }

    public FreyaOrderService getFreyaOrderService()
    {
        return freyaOrderService;
    }
}