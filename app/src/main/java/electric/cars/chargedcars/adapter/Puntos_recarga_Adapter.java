package electric.cars.chargedcars.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import electric.cars.chargedcars.InfoRecargaActivity;
import electric.cars.chargedcars.MigraDatosActivity;
import electric.cars.chargedcars.R;
import electric.cars.chargedcars.model.Sitios_recarga;

public class Puntos_recarga_Adapter extends RecyclerView.Adapter<Puntos_recarga_Adapter.ViewHolder> {


    private static List<Sitios_recarga> puntosRecargaList;

    public Puntos_recarga_Adapter(List<Sitios_recarga> puntosRecargaList) {

        this.puntosRecargaList = puntosRecargaList;



    }


    @Override
    public int getItemCount() {

        return puntosRecargaList.size();
    }



    @NonNull
    @Override
    public Puntos_recarga_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_recarga, parent,false);




        /*List<String> listaRecorte = milista.get(0).subList(0,1);
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: " + listaRecorte);*/

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Puntos_recarga_Adapter.ViewHolder holder, int position) {


        holder.txtNombreCard.setText(puntosRecargaList.get(position).getNombre_punto());
        //
        //holder.txtDispoCard.setText(puntosRecargaList.get(position).getDisponibilidad_punto());
        //holder.txtDirectCard.setText(puntosRecargaList.get(position).getDireccion_punto());
        //holder.txtTelefCard.setText(puntosRecargaList.get(position).getTelefono_punto());
        //
        holder.txtGrupoCard.setText(puntosRecargaList.get(position).getGrupo_punto());

        Picasso.get()
                .load(puntosRecargaList.get(position).getUriImage())
                .resize(300,300)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_hide_image_24_lost_image)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(holder.imgLogoCard);

        holder.position = holder.getAdapterPosition();




    }

    static ArrayList<String> milistaNombre = new ArrayList<String>();
    static ArrayList<String> milistaGrupo = new ArrayList<String>();
    static ArrayList<String> milistaLatitud = new ArrayList<String>();
    static ArrayList<String> milistaLongitud = new ArrayList<String>();
    static ArrayList<String> milistaImagenLogo = new ArrayList<String>();
    static int vueltas = 0;
    static int z = 0;

    public static class ViewHolder extends RecyclerView.ViewHolder {



        private ImageView imgLogoCard;
        private TextView txtNombreCard, txtDispoCard, txtDirectCard, txtTelefCard, txtGrupoCard;

        private int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgLogoCard = itemView.findViewById(R.id.imgLogoCard);
            txtNombreCard = itemView.findViewById(R.id.txtNombreCard);
            //txtDispoCard = itemView.findViewById(R.id.txtDispoCard);
            //txtDirectCard = itemView.findViewById(R.id.txtDirectCard);
            //txtTelefCard = itemView.findViewById(R.id.txtTelefCard);
            txtGrupoCard = itemView.findViewById(R.id.txtGrupoCard);


            if(vueltas == puntosRecargaList.size() && z == 0){

                Intent intento = new Intent(itemView.getContext(), MigraDatosActivity.class);
                intento.putStringArrayListExtra("miListaNombre", milistaNombre);
                intento.putStringArrayListExtra("miListaGrupo", milistaGrupo);
                intento.putStringArrayListExtra("miListaLatitud", milistaLatitud);
                intento.putStringArrayListExtra("miListaLongitud", milistaLongitud);
                intento.putStringArrayListExtra("milistaImagenLogo", milistaImagenLogo);


                intento.putExtra("arraySize",puntosRecargaList.size());
                //itemView.getContext().startActivity(intento);



                z++;
            }


            while(vueltas < puntosRecargaList.size()){

                milistaNombre.add(String.valueOf(puntosRecargaList.get(vueltas).getNombre_punto()));
                milistaGrupo.add(String.valueOf(puntosRecargaList.get(vueltas).getGrupo_punto()));
                milistaLatitud.add(String.valueOf(puntosRecargaList.get(vueltas).getLatitud()));
                milistaLongitud.add(String.valueOf(puntosRecargaList.get(vueltas).getLongitud()));
                milistaImagenLogo.add(String.valueOf(puntosRecargaList.get(vueltas).getUriImage()));



                vueltas++;
            }



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), InfoRecargaActivity.class);
                    intent.putExtra("nombre", puntosRecargaList.get(position).getNombre_punto());
                    intent.putExtra("direct", puntosRecargaList.get(position).getDireccion_punto());
                    intent.putExtra("telef", puntosRecargaList.get(position).getTelefono_punto());
                    intent.putExtra("grupo", puntosRecargaList.get(position).getGrupo_punto());
                    intent.putExtra("idUsuario",puntosRecargaList.get(position).getIdUsario());
                    intent.putExtra("longitud",puntosRecargaList.get(position).getLongitud());
                    intent.putExtra("latitud",puntosRecargaList.get(position).getLatitud());
                    intent.putExtra("urlImage",puntosRecargaList.get(position).getUriImage());



                    view.getContext().startActivity(intent);
                    //Toast.makeText(view.getContext(), "Click en cardView " + position, Toast.LENGTH_SHORT).show();



                }






            });

        }
    }


}












































