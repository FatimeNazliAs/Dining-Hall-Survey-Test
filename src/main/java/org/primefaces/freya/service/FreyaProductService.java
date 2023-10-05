/**
 * Copyright 2009-2020 PrimeTek.
 * <p>
 * Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.freya.service;

import java.util.ArrayList;
import java.util.List;


import org.primefaces.freya.domain.InventoryStatus;
import org.primefaces.freya.domain.Product;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;


@ManagedBean(name = "freyaProductService")
@ApplicationScoped
public class FreyaProductService
{

    public List<Product> getProducts()
    {
        List<Product> products = new ArrayList<>();

        products.add(new Product(1000, "nvklal433", "Süzme Çiçek Balı", "Ürün Tanımı", "cicek_bali.jpg", 75, "Gıda", 61, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1000, "nvklal433", "Petek Balı", "Ürün Tanımı", "honey-petekbal.png", 75, "Gıda", 61, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1002, "f230fh0g3", "Atom", "Ürün Tanımı", "honey-atom.jpg", 100, "Gıda", 24, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1001, "zz21cz3c1", "Saf Propolis", "Ürün Tanımı", "safpropolis.jpg", 100, "Gıda", 2, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1000, "nvklal433", "Karakovan Petek Balı", "Ürün Tanımı", "cicek_bali.jpg", 140, "Gıda", 61, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1004, "nvklal433", "Mağara Kaya Balı", "Ürün Tanımı", "honey-magarakayabali.png", 220, "Gıda", 61, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1000, "nvklal433", "Süzme Çiçek Balı", "Ürün Tanımı", "cicek_bali.jpg", 75, "Gıda", 61, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1000, "nvklal433", "Petek Balı", "Ürün Tanımı", "honey-petekbal.png", 75, "Gıda", 61, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1002, "f230fh0g3", "Atom", "Ürün Tanımı", "honey-atom.jpg", 100, "Gıda", 24, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1001, "zz21cz3c1", "Saf Propolis", "Ürün Tanımı", "safpropolis.jpg", 100, "Gıda", 2, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1000, "nvklal433", "Karakovan Petek Balı", "Ürün Tanımı", "cicek_bali.jpg", 140, "Gıda", 61, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1004, "nvklal433", "Mağara Kaya Balı", "Ürün Tanımı", "honey-magarakayabali.png", 220, "Gıda", 61, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1000, "nvklal433", "Süzme Çiçek Balı", "Ürün Tanımı", "cicek_bali.jpg", 75, "Gıda", 61, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1000, "nvklal433", "Petek Balı", "Ürün Tanımı", "honey-petekbal.png", 75, "Gıda", 61, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1002, "f230fh0g3", "Atom", "Ürün Tanımı", "honey-atom.jpg", 100, "Gıda", 24, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1001, "zz21cz3c1", "Saf Propolis", "Ürün Tanımı", "safpropolis.jpg", 100, "Gıda", 2, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1000, "nvklal433", "Karakovan Petek Balı", "Ürün Tanımı", "cicek_bali.jpg", 140, "Gıda", 61, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1004, "nvklal433", "Mağara Kaya Balı", "Ürün Tanımı", "honey-magarakayabali.png", 220, "Gıda", 61, InventoryStatus.INSTOCK, 5));


        return products;
    }

}