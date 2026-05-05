$fn = 120;

// ===== PARAMETERS =====
radius = 30;
layer_height = 24;
layers = 6;
gap = 3;
lip = 2.4;        // slight edge thickness
squash = 0.35;    // vertical squash for ellipse look

// ===== MODULE =====
module db_layer(r, h, lip) {
    union() {
        // Main disc
        scale([1, 1, squash])
            cylinder(h = h, r = r);

        // Top lip (slight highlight edge)
        translate([0, 0, h * squash])
            scale([1, 1, squash])
                difference() {
                    cylinder(h = lip, r = r);
                    cylinder(h = lip + 0.1, r = r - 2);
                }
    }
}

// ===== STACK =====
for (i = [0 : layers - 1]) {
    translate([0, 0, i * (layer_height * squash + gap)])
        db_layer(radius, layer_height, lip);
}
