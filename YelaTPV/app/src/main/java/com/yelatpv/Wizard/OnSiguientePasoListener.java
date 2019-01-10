package com.yelatpv.Wizard;

import com.yelatpv.ClasesOBJ.Empresa;
import com.yelatpv.ClasesOBJ.Usuario;

/**
 * Created by pablosirvent on 5/3/18.
 */

public interface OnSiguientePasoListener {
    public void OnSiguientePaso(int pasosiguiente, Empresa e, Usuario u);
}
